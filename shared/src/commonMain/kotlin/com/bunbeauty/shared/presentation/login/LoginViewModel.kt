package com.bunbeauty.shared.presentation.login

import com.bunbeauty.shared.domain.exeptions.InvalidPhoneNumberException
import com.bunbeauty.shared.domain.exeptions.TooManyRequestsException
import com.bunbeauty.shared.domain.feature.login.FormatPhoneNumberUseCase
import com.bunbeauty.shared.domain.feature.login.GetPhoneNumberCursorPositionUseCase
import com.bunbeauty.shared.domain.use_case.auth.RequestCodeUseCase
import com.bunbeauty.shared.extension.launchSafe
import com.bunbeauty.shared.presentation.SharedStateViewModel

class LoginViewModel(
    private val requestCode: RequestCodeUseCase,
    private val formatPhoneNumber: FormatPhoneNumberUseCase,
    private val getPhoneNumberCursorPosition: GetPhoneNumberCursorPositionUseCase,
) : SharedStateViewModel<LoginState, LoginAction, LoginEvent>(LoginState()) {

    override fun handleAction(action: LoginAction) {
        when (action) {
            LoginAction.Init -> init()
            is LoginAction.ChangePhoneNumber -> updatePhoneNumber(action.phoneNumber, action.cursorPosition)
            LoginAction.NextClick -> requestCode()
            is LoginAction.BackClick -> navigateBack()
            is LoginAction.ConsumeEvents -> consumeEvents(action.eventList)
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
                    LoginEvent.NavigateToConfirmEvent(state.phoneNumber)
                }
            },
            onError = ::handleException
        )
    }

    private fun navigateBack() {
        event {
            LoginEvent.NavigateBack
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
                    LoginEvent.ShowTooManyRequestsErrorEvent
                }
            }

            else -> {
                event {
                    LoginEvent.ShowSomethingWentWrongErrorEvent
                }
            }
        }
    }

}
