package com.bunbeauty.shared.presentation.confirm

import com.bunbeauty.shared.domain.exeptions.AuthSessionTimeoutException
import com.bunbeauty.shared.domain.exeptions.InvalidCodeException
import com.bunbeauty.shared.domain.exeptions.NoAttemptsException
import com.bunbeauty.shared.domain.exeptions.TooManyRequestsException
import com.bunbeauty.shared.domain.feature.login.FormatPhoneNumberUseCase
import com.bunbeauty.shared.domain.model.SuccessLoginDirection
import com.bunbeauty.shared.domain.use_case.auth.CheckCodeUseCase
import com.bunbeauty.shared.domain.use_case.auth.ResendCodeUseCase
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
) : SharedStateViewModel<Confirm.State, Confirm.Action, Confirm.Event>(
    initState = Confirm.State(
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

    override fun handleAction(action: Confirm.Action) {
        when (action) {
            is Confirm.Action.Init -> {
                init(direction = action.direction, phoneNumber = action.phoneNumber)
            }

            is Confirm.Action.CheckCode -> {
                startCodeChecking(action.code)
            }

            Confirm.Action.ResendCode -> {
                startCodeResending()
            }

            Confirm.Action.BackClick -> {
                event { Confirm.Event.NavigateBack }
            }
        }
    }

    private fun init(direction: SuccessLoginDirection, phoneNumber: String) {
        this.direction = direction
        state { state ->
            state.copy(phoneNumber = formatPhoneNumber(phoneNumber))
        }
    }

    private fun startCodeChecking(code: String) {
        state { state ->
            state.copy(isLoading = true)
        }
        sharedScope.launchSafe(
            block = {
                checkCode(code)
                finishConfirmation()
            },
            onError = { throwable ->
                state { state ->
                    state.copy(isLoading = false)
                }
                handleException(throwable)
            }
        )
    }

    private fun finishConfirmation() {
        direction?.let { direction ->
            event {
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
        event {
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
        state { state ->
            state.copy(resendSeconds = TIMER_SECONDS)
        }
        timerJob = sharedScope.launch {
            while (state.value.resendSeconds > 0) {
                delay(TIMER_INTERVAL_MILLIS)
                state { state ->
                    state.copy(resendSeconds = state.resendSeconds - 1)
                }
            }
        }
        timerJob?.start()
    }
}