package com.bunbeauty.shared.presentation.login

import com.bunbeauty.shared.domain.exeptions.InvalidPhoneNumberException
import com.bunbeauty.shared.domain.exeptions.TooManyRequestsException
import com.bunbeauty.shared.domain.feature.auth.FormatPhoneNumberUseCase
import com.bunbeauty.shared.domain.feature.auth.GetPhoneNumberCursorPositionUseCase
import com.bunbeauty.shared.domain.feature.auth.RequestCodeUseCase
import com.bunbeauty.shared.extension.launchSafe
import com.bunbeauty.shared.presentation.base.SharedStateViewModel

class LoginViewModel(
    private val requestCode: RequestCodeUseCase,
    private val formatPhoneNumber: FormatPhoneNumberUseCase,
    private val getPhoneNumberCursorPosition: GetPhoneNumberCursorPositionUseCase,
) : SharedStateViewModel<Login.State, Login.Action, Login.Event>(Login.State()) {

    override fun handleAction(action: Login.Action) {
        when (action) {
            Login.Action.Init -> init()
            is Login.Action.ChangePhoneNumber -> updatePhoneNumber(
                phoneNumber = action.phoneNumber,
                cursorPosition = action.cursorPosition
            )
            Login.Action.NextClick -> requestCode()
            is Login.Action.BackClick -> navigateBack()
            is Login.Action.ConsumeEvents -> consumeEvents(action.eventList)
        }
    }

    private fun init() {
        state { state ->
            state.copy(isLoading = false)
        }
    }

    private fun updatePhoneNumber(phoneNumber: String, cursorPosition: Int) {
        state { state ->
            state.copy(
                phoneNumber = formatPhoneNumber(phoneNumber),
                phoneNumberCursorPosition = getPhoneNumberCursorPosition(phoneNumber, cursorPosition)
            )
        }
    }

    private fun requestCode() {
        state { state ->
            state.copy(
                hasPhoneError = false,
                isLoading = true
            )
        }

        sharedScope.launchSafe(
            block = {
                requestCode(mutableState.value.phoneNumber)
                event { state ->
                    Login.Event.NavigateToConfirm(state.phoneNumber)
                }
            },
            onError = ::handleException
        )
    }

    private fun navigateBack() {
        event {
            Login.Event.NavigateBack
        }
    }

    private fun handleException(throwable: Throwable) {
        state { state ->
            state.copy(isLoading = false)
        }

        when (throwable) {
            is InvalidPhoneNumberException -> {
                state { state ->
                    state.copy(hasPhoneError = true)
                }
            }

            is TooManyRequestsException -> {
                event {
                    Login.Event.ShowTooManyRequestsError
                }
            }

            else -> {
                event {
                    Login.Event.ShowSomethingWentWrongError
                }
            }
        }
    }

}
