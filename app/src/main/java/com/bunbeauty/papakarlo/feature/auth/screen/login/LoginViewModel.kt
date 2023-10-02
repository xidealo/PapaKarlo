package com.bunbeauty.papakarlo.feature.auth.screen.login

import com.bunbeauty.shared.Constants.PHONE_CODE
import com.bunbeauty.shared.domain.exeptions.InvalidPhoneNumberException
import com.bunbeauty.shared.domain.exeptions.TooManyRequestsException
import com.bunbeauty.shared.domain.use_case.auth.RequestCodeUseCase
import com.bunbeauty.shared.extension.launchSafe
import com.bunbeauty.shared.presentation.SharedViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel(
    private val requestCodeUseCase: RequestCodeUseCase,
) : SharedViewModel() {

    private val mutableLoginState = MutableStateFlow(LoginState())
    val loginState = mutableLoginState.asStateFlow()

    fun handleAction(action: LoginAction) {
        when (action) {
            LoginAction.Init -> init()
            is LoginAction.ChangePhoneNumber -> updatePhoneNumber(action.phoneNumber, action.cursorPosition)
            LoginAction.NextClick -> requestCode()
            is LoginAction.BackClick -> navigateBack()
            is LoginAction.ConsumeEvents -> consumeEventList(action.eventList)
        }
    }

    private fun init() {
        mutableLoginState.update { state ->
            state.copy(isLoading = false)
        }
    }

    private fun updatePhoneNumber(phoneNumber: String, cursorPosition: Int) {
        mutableLoginState.update { state ->
            state.copy(
                phoneNumber = formatPhoneNumber(phoneNumber),
                phoneNumberCursorPosition = getNewPosition(phoneNumber, cursorPosition)
            )
        }
    }

    private fun formatPhoneNumber(phoneNumber: String): String {
        val numbers = phoneNumber.run {
            if (!contains(PHONE_CODE) && isNotEmpty()) {
                ""
            } else {
                this
            }
        }.replace(PHONE_CODE, "")
            .replace(Regex("\\D"), "")
        val firstGroup = numbers.take(3)
        val secondGroup = numbers.drop(3).take(3)
        val thirdGroup = numbers.drop(6).take(2)
        val fourthGroup = numbers.drop(8).take(2)
        var result = PHONE_CODE
        if (firstGroup.isNotEmpty()) {
            result += " ($firstGroup"
        }
        if (secondGroup.isNotEmpty()) {
            result += ") $secondGroup"
        }
        if (thirdGroup.isNotEmpty()) {
            result += "-$thirdGroup"
        }
        if (fourthGroup.isNotEmpty()) {
            result += "-$fourthGroup"
        }

        return result
    }

    private fun getNewPosition(phoneNumber: String, cursorPosition: Int): Int {
        var newPosition = cursorPosition
        when (cursorPosition) {
            0, 1 -> {
                newPosition = 2
            }

            3 -> {
                // "+70" -> "+7 (0"
                if (phoneNumber[2].isNumber()) {
                    newPosition = 5
                }
            }

            8 -> {
                // "+7 (0000" -> "+7 (000) 0"
                if (phoneNumber[7].isNumber()) {
                    newPosition = 10
                }
            }

            13 -> {
                // "+7 (000) 0000" -> "+7 (000) 000-0"
                if (phoneNumber[12].isNumber()) {
                    newPosition = 14
                }
            }

            16 -> {
                // "+7 (000) 000-000" -> "+7 (000) 000-00-0"
                if (phoneNumber[15].isNumber()) {
                    newPosition = 17
                }
            }
        }

        val formatPhoneNumber = formatPhoneNumber(phoneNumber)
        return if (newPosition > formatPhoneNumber.length) {
            formatPhoneNumber.length
        } else {
            newPosition
        }
    }

    private fun requestCode() {
        mutableLoginState.update { state ->
            state.copy(
                hasPhoneError = false,
                isLoading = true
            )
        }

        sharedScope.launchSafe(
            block =  {
                requestCodeUseCase(mutableLoginState.value.phoneNumber)
                mutableLoginState.update { state ->
                    state + LoginEvent.NavigateToConfirmEvent(mutableLoginState.value.phoneNumber)
                }
            },
            onError = ::handleException
        )
    }

    private fun navigateBack() {
        mutableLoginState.update { state ->
            state + LoginEvent.NavigateBack
        }
    }

    private fun handleException(throwable : Throwable) {
        mutableLoginState.update { state ->
            val updatedState = state.copy(isLoading = false)
            when (throwable) {
                is InvalidPhoneNumberException -> {
                    updatedState.copy(hasPhoneError = true)
                }

                is TooManyRequestsException -> {
                    updatedState + LoginEvent.ShowTooManyRequestsErrorEvent
                }

                else -> {
                    updatedState + LoginEvent.ShowSomethingWentWrongErrorEvent
                }
            }
        }
    }

    private fun consumeEventList(eventList: List<LoginEvent>) {
        mutableLoginState.update { state ->
            state.copy(eventList = state.eventList - eventList.toSet())
        }
    }

    private fun Char.isNumber(): Boolean {
        return Regex("\\d").matches(this.toString())
    }
}
