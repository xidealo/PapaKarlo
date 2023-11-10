package com.bunbeauty.shared.presentation.confirm

import com.bunbeauty.analytic.AnalyticService
import com.bunbeauty.analytic.event.ConfirmErrorShowEvent
import com.bunbeauty.shared.domain.exeptions.AuthSessionTimeoutException
import com.bunbeauty.shared.domain.exeptions.InvalidCodeException
import com.bunbeauty.shared.domain.exeptions.NoAttemptsException
import com.bunbeauty.shared.domain.exeptions.TooManyRequestsException
import com.bunbeauty.shared.domain.feature.auth.FormatPhoneNumberUseCase
import com.bunbeauty.shared.domain.model.SuccessLoginDirection
import com.bunbeauty.shared.domain.feature.auth.CheckCodeUseCase
import com.bunbeauty.shared.domain.feature.auth.ResendCodeUseCase
import com.bunbeauty.shared.extension.launchSafe
import com.bunbeauty.shared.presentation.base.SharedStateViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TIMER_INTERVAL_MILLIS = 1_000L
private const val TIMER_SECONDS = 60

class ConfirmViewModel(
    private val formatPhoneNumber: FormatPhoneNumberUseCase,
    private val checkCode: CheckCodeUseCase,
    private val resendCode: ResendCodeUseCase,
    private val analyticService: AnalyticService,
) : SharedStateViewModel<Confirm.ViewDataState, Confirm.Action, Confirm.Event>(
    initDataState = Confirm.ViewDataState(
        phoneNumber = "",
        resendSeconds = TIMER_SECONDS,
        isLoading = false,
    )
) {

    private var timerJob: Job? = null

    private var direction: SuccessLoginDirection? = null

    init {
        startResendTimer()
    }

    override fun reduce(action: Confirm.Action, dataState: Confirm.ViewDataState) {
        when (action) {
            is Confirm.Action.Init -> {
                init(
                    direction = action.direction,
                    phoneNumber = action.phoneNumber
                )
            }

            is Confirm.Action.CheckCode -> {
                startCodeChecking(action.code)
            }

            Confirm.Action.ResendCode -> {
                startCodeResending()
            }

            Confirm.Action.BackClick -> {
                addEvent { Confirm.Event.NavigateBack }
            }
        }
    }

    private fun init(direction: SuccessLoginDirection, phoneNumber: String) {
        this.direction = direction
        setState {
            copy(phoneNumber = formatPhoneNumber(phoneNumber))
        }
    }

    private fun startCodeChecking(code: String) {
        setState {
            copy(isLoading = true)
        }
        sharedScope.launchSafe(
            block = {
                checkCode(code)
                finishConfirmation()
            },
            onError = { throwable ->
                setState {
                    copy(isLoading = false)
                }
                handleException(throwable)
            }
        )
    }

    private fun finishConfirmation() {
        direction?.let { direction ->
            addEvent {
                when (direction) {
                    SuccessLoginDirection.BACK_TO_PROFILE -> Confirm.Event.NavigateBackToProfile
                    SuccessLoginDirection.TO_CREATE_ORDER -> Confirm.Event.NavigateToCreateOrder
                }
            }
        }
    }

    private fun startCodeResending() {
        startResendTimer()
        sharedScope.launchSafe(
            block = {
                resendCode()
            },
            onError = ::handleException
        )
    }

    private fun handleException(throwable: Throwable) {
        analyticService.sendEvent(
            event = ConfirmErrorShowEvent(
                error = when (throwable) {
                    is TooManyRequestsException -> "TooManyRequests"
                    is NoAttemptsException -> "NoAttempts"
                    is InvalidCodeException -> "InvalidCode"
                    is AuthSessionTimeoutException -> "AuthSessionTimeout"
                    else -> "ShowSomethingWentWrong"
                }
            ),
        )

        addEvent {
            when (throwable) {
                is TooManyRequestsException -> Confirm.Event.ShowTooManyRequestsError
                is NoAttemptsException -> Confirm.Event.ShowNoAttemptsError
                is InvalidCodeException -> Confirm.Event.ShowInvalidCodeError
                is AuthSessionTimeoutException -> Confirm.Event.ShowAuthSessionTimeoutError
                else -> Confirm.Event.ShowSomethingWentWrongError
            }
        }
    }

    private fun startResendTimer() {
        setState {
            copy(resendSeconds = TIMER_SECONDS)
        }
        timerJob = sharedScope.launch {
            while (dataState.value.resendSeconds > 0) {
                delay(TIMER_INTERVAL_MILLIS)
                setState {
                    copy(resendSeconds = resendSeconds - 1)
                }
            }
        }
        timerJob?.start()
    }
}