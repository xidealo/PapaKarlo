package com.bunbeauty.papakarlo.feature.auth.screen.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.common.model.SuccessLoginDirection
import com.bunbeauty.papakarlo.common.model.SuccessLoginDirection.BACK_TO_PROFILE
import com.bunbeauty.papakarlo.common.model.SuccessLoginDirection.TO_CREATE_ORDER
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.util.text_validator.ITextValidator
import com.bunbeauty.shared.Constants.PHONE_CODE
import com.bunbeauty.shared.data.FirebaseAuthRepository
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val textValidator: ITextValidator,
    private val userInteractor: IUserInteractor,
    private val firebaseAuthRepository: FirebaseAuthRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val mutableLoginState = MutableStateFlow(LoginState())
    val loginState = mutableLoginState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        mutableLoginState.update { oldState ->
            oldState.copy(
                state = LoginState.State.Error(throwable)
            )
        }
    }

    private val successLoginDirection: SuccessLoginDirection by lazy {
        savedStateHandle["successLoginDirection"] ?: BACK_TO_PROFILE
    }

    fun setSuccessState() {
        mutableLoginState.update { oldState ->
            oldState.copy(
                state = LoginState.State.Success
            )
        }
    }

    fun formatPhoneNumber(inputPhoneNumber: String): String {
        val numbers = inputPhoneNumber.run {
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

    fun getNewPosition(inputPhoneNumber: String, cursorPosition: Int): Int {
        var newPosition = cursorPosition
        when (cursorPosition) {
            0, 1 -> {
                newPosition = 2
            }
            3 -> {
                // "+70" -> "+7 (0"
                if (inputPhoneNumber[2].isNumber()) {
                    newPosition = 5
                }
            }
            8 -> {
                // "+7 (0000" -> "+7 (000) 0"
                if (inputPhoneNumber[7].isNumber()) {
                    newPosition = 10
                }
            }
            13 -> {
                // "+7 (000) 0000" -> "+7 (000) 000-0"
                if (inputPhoneNumber[12].isNumber()) {
                    newPosition = 14
                }
            }
            16 -> {
                // "+7 (000) 000-000" -> "+7 (000) 000-00-0"
                if (inputPhoneNumber[15].isNumber()) {
                    newPosition = 17
                }
            }
        }

        val phoneNumber = formatPhoneNumber(inputPhoneNumber)
        return if (newPosition > phoneNumber.length) {
            phoneNumber.length
        } else {
            newPosition
        }
    }

    fun onPhoneTextChanged(phone: String) {
        mutableLoginState.update { oldState ->
            oldState.copy(
                phone = phone
            )
        }
    }

    fun onNextClick() {
        val phone = mutableLoginState.value.phone

        mutableLoginState.update { oldState ->
            oldState.copy(
                hasPhoneError = false
            )
        }

        if (!textValidator.isPhoneNumberCorrect(phone)) {
            mutableLoginState.update { oldState ->
                oldState.copy(
                    hasPhoneError = true
                )
            }
            return
        }

        mutableLoginState.update { oldState ->
            oldState.copy(
                state = LoginState.State.Loading,
                eventList = oldState.eventList + LoginState.Event.SendCodeEvent(phone = phone)
            )
        }
    }

    fun onSuccessVerified() {
        viewModelScope.launch(exceptionHandler) {
            userInteractor.login(
                firebaseUserUuid = firebaseAuthRepository.firebaseUserUuid,
                firebaseUserPhone = firebaseAuthRepository.firebaseUserPhone
            )

            when (successLoginDirection) {
                BACK_TO_PROFILE -> mutableLoginState.update { state ->
                    state.copy(
                        eventList = state.eventList + LoginState.Event.NavigateBackToProfileEvent
                    )
                }
                TO_CREATE_ORDER -> mutableLoginState.update { state ->
                    state.copy(
                        eventList = state.eventList + LoginState.Event.NavigateToCreateOrderEvent
                    )
                }
            }
        }
    }

    fun onVerificationError(error: String) {
        mutableLoginState.update { state ->
            state.copy(state = LoginState.State.Success) + LoginState.Event.ShowErrorEvent(error)
        }
    }

    fun onCodeSent(phone: String) {
        mutableLoginState.update { state ->
            state.copy(
                eventList = state.eventList + LoginState.Event.NavigateToConfirmEvent(
                    phone = phone,
                    successLoginDirection = successLoginDirection
                )
            )
        }
    }

    fun consumeEventList(eventList: List<LoginState.Event>) {
        mutableLoginState.update { state ->
            state.copy(eventList = state.eventList - eventList.toSet())
        }
    }

    private fun Char.isNumber(): Boolean {
        return Regex("\\d").matches(this.toString())
    }
}
