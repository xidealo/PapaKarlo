package com.bunbeauty.shared.presentation.login

import com.bunbeauty.core.domain.exeptions.InvalidPhoneNumberException
import com.bunbeauty.core.domain.exeptions.TooManyRequestsException
import com.bunbeauty.core.domain.auth.CheckPhoneNumberUseCase
import com.bunbeauty.core.domain.auth.FormatPhoneNumberUseCase
import com.bunbeauty.core.domain.auth.GetPhoneNumberCursorPositionUseCase
import com.bunbeauty.core.extension.launchSafe
import com.bunbeauty.core.base.SharedStateViewModel
import com.bunbeauty.core.domain.auth.RequestCodeUseCase

class LoginViewModel(
    private val requestCode: RequestCodeUseCase,
    private val formatPhoneNumber: FormatPhoneNumberUseCase,
    private val getPhoneNumberCursorPosition: GetPhoneNumberCursorPositionUseCase,
    private val checkPhoneNumber: CheckPhoneNumberUseCase,
) : SharedStateViewModel<Login.ViewDataState, Login.Action, Login.Event>(Login.ViewDataState()) {
    override fun reduce(
        action: Login.Action,
        dataState: Login.ViewDataState,
    ) {
        when (action) {
            Login.Action.Init -> init()
            is Login.Action.ChangePhoneNumber ->
                updatePhoneNumber(
                    phoneNumber = action.phoneNumber,
                    cursorPosition = action.cursorPosition,
                )

            Login.Action.NextClick -> requestCode()
            is Login.Action.BackClick -> navigateBack()
        }
    }

    private fun init() {
        setState {
            copy(isLoading = false)
        }
    }

    private fun updatePhoneNumber(
        phoneNumber: String,
        cursorPosition: Int,
    ) {
        setState {
            copy(
                phoneNumber = formatPhoneNumber(phoneNumber),
                phoneNumberCursorPosition =
                    getPhoneNumberCursorPosition(
                        phoneNumber = phoneNumber,
                        cursorPosition = cursorPosition,
                    ),
            )
        }
    }

    private fun requestCode() {
        val phoneNumber =
            mutableDataState.value.phoneNumber.replace(
                regex = Regex("[ ()-]"),
                replacement = "",
            )
        if (checkPhoneNumber(phoneNumber)) {
            setState {
                copy(
                    hasPhoneError = false,
                    isLoading = true,
                )
            }
        } else {
            setState {
                copy(hasPhoneError = true)
            }
            return
        }

        sharedScope.launchSafe(
            block = {
                requestCode(phoneNumber)
                addEvent {
                    Login.Event.NavigateToConfirm(phoneNumber)
                }
            },
            onError = ::handleException,
        )
    }

    private fun navigateBack() {
        addEvent {
            Login.Event.NavigateBack
        }
    }

    private fun handleException(throwable: Throwable) {
        setState {
            copy(isLoading = false)
        }

        when (throwable) {
            is InvalidPhoneNumberException -> {
                setState {
                    copy(hasPhoneError = true)
                }
            }

            is TooManyRequestsException -> {
                addEvent {
                    Login.Event.ShowTooManyRequestsError
                }
            }

            else -> {
                addEvent {
                    Login.Event.ShowSomethingWentWrongError
                }
            }
        }
    }
}
