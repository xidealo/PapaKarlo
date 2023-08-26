package com.bunbeauty.papakarlo.feature.auth.screen.confirm

import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.common.model.SuccessLoginDirection
import com.bunbeauty.papakarlo.common.model.SuccessLoginDirection.BACK_TO_PROFILE
import com.bunbeauty.papakarlo.common.model.SuccessLoginDirection.TO_CREATE_ORDER
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.feature.auth.model.ConfirmState
import com.bunbeauty.papakarlo.feature.auth.model.ConfirmState.ConfirmError.SOMETHING_WENT_WRONG_ERROR
import com.bunbeauty.papakarlo.feature.auth.model.ConfirmState.ConfirmError.WRONG_CODE_ERROR
import com.bunbeauty.shared.Constants.WRONG_CODE
import com.bunbeauty.shared.Logger.AUTH_TAG
import com.bunbeauty.shared.Logger.logD
import com.bunbeauty.shared.data.FirebaseAuthRepository
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ConfirmViewModel(
    private val userInteractor: IUserInteractor,
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val successLoginDirection: SuccessLoginDirection,
    phoneNumber: String
) : BaseViewModel() {

    private val timerSecondCount = 60
    private val timerIntervalMillis = 1000L

    private var timerJob: Job? = null

    private val mutableConfirmState: MutableStateFlow<ConfirmState> =
        MutableStateFlow(
            ConfirmState(
                phoneNumber = phoneNumber,
                resendSeconds = timerSecondCount,
                isCodeChecking = false
            )
        )
    val confirmState: StateFlow<ConfirmState> = mutableConfirmState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        mutableConfirmState.update { state ->
            state.copy(isCodeChecking = false) + ConfirmState.Event.ShowErrorMessageEvent(
                SOMETHING_WENT_WRONG_ERROR
            )
        }
    }

    init {
        startResendTimer()
    }

    fun onCodeEntered() {
        mutableConfirmState.value = mutableConfirmState.value.copy(isCodeChecking = true)
    }

    fun onResendCodeClicked() {
        startResendTimer()
    }

    fun onVerificationError(error: String) {
        mutableConfirmState.value = mutableConfirmState.value.copy(isCodeChecking = false)
        val confirmError = when (error) {
            WRONG_CODE -> WRONG_CODE_ERROR
            else -> SOMETHING_WENT_WRONG_ERROR
        }
        mutableConfirmState.update { state ->
            state.copy(isCodeChecking = false) + ConfirmState.Event.ShowErrorMessageEvent(
                confirmError
            )
        }
        logD(AUTH_TAG, error)
    }

    fun onSuccessVerified() {
        viewModelScope.launch(exceptionHandler) {
            userInteractor.login(
                firebaseUserUuid = firebaseAuthRepository.firebaseUserUuid,
                firebaseUserPhone = firebaseAuthRepository.firebaseUserPhone
            )

            val navigateEvent = when (successLoginDirection) {
                BACK_TO_PROFILE -> ConfirmState.Event.NavigateBackToProfileEvent
                TO_CREATE_ORDER -> ConfirmState.Event.NavigateToCreateOrderEvent
            }
            mutableConfirmState.update { state ->
                state + navigateEvent
            }
        }
    }

    fun consumeEventList(eventList: List<ConfirmState.Event>) {
        mutableConfirmState.update { state ->
            state - eventList
        }
    }

    private fun startResendTimer() {
        mutableConfirmState.value = mutableConfirmState.value.copy(resendSeconds = timerSecondCount)
        timerJob = viewModelScope.launch(exceptionHandler) {
            while (mutableConfirmState.value.resendSeconds > 0) {
                delay(timerIntervalMillis)
                mutableConfirmState.value = mutableConfirmState.value.run {
                    copy(resendSeconds = resendSeconds - 1)
                }
            }
        }.apply {
            start()
        }
    }
}
