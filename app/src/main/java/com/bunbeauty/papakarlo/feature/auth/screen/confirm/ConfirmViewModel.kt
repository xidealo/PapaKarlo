package com.bunbeauty.papakarlo.feature.auth.screen.confirm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.model.SuccessLoginDirection
import com.bunbeauty.papakarlo.common.model.SuccessLoginDirection.BACK_TO_PROFILE
import com.bunbeauty.papakarlo.common.model.SuccessLoginDirection.TO_CREATE_ORDER
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.feature.auth.FirebaseAuthRepository
import com.bunbeauty.papakarlo.feature.auth.model.Confirmation
import com.bunbeauty.papakarlo.feature.auth.screen.confirm.ConfirmFragmentDirections.backToProfileFragment
import com.bunbeauty.papakarlo.feature.auth.screen.confirm.ConfirmFragmentDirections.toCreateOrderFragment
import com.bunbeauty.shared.Constants.WRONG_CODE
import com.bunbeauty.shared.Logger.AUTH_TAG
import com.bunbeauty.shared.Logger.logD
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import com.google.firebase.auth.PhoneAuthProvider
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
    savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val successLoginDirection: SuccessLoginDirection =
        savedStateHandle["successLoginDirection"]!!

    private val timerSecondCount = 60
    private val timerIntervalMillis = 1000L

    private var timerJob: Job? = null

    private val mutableConfirmState: MutableStateFlow<Confirmation> =
        MutableStateFlow(
            Confirmation(
                phoneNumber = savedStateHandle["phone"]!!,
                resendToken = savedStateHandle["resendToken"]!!,
                verificationId = savedStateHandle["verificationId"]!!,
                resendSeconds = timerSecondCount,
                isCodeChecking = false
            )
        )
    val confirmState: StateFlow<Confirmation> = mutableConfirmState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        showError(resourcesProvider.getString(R.string.error_something_went_wrong), true)

        mutableConfirmState.update { oldState ->
            oldState.copy(isCodeChecking = false)
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

    fun onCodeSent(verificationId: String, resendToken: PhoneAuthProvider.ForceResendingToken) {
        mutableConfirmState.value = mutableConfirmState.value.copy(
            verificationId = verificationId,
            resendToken = resendToken
        )
    }

    fun onVerificationError(error: String) {
        mutableConfirmState.value = mutableConfirmState.value.copy(isCodeChecking = false)
        val errorResourceId = when (error) {
            WRONG_CODE -> {
                R.string.error_confirm_wrong_code
            }
            else -> {
                R.string.error_something_went_wrong
            }
        }
        showError(resourcesProvider.getString(errorResourceId), true)
        logD(AUTH_TAG, error)
    }

    fun onSuccessVerified() {
        viewModelScope.launch(exceptionHandler) {
            userInteractor.login(
                firebaseUserUuid = firebaseAuthRepository.firebaseUserUuid,
                firebaseUserPhone = firebaseAuthRepository.firebaseUserPhone
            )

            when (successLoginDirection) {
                BACK_TO_PROFILE -> router.navigate(backToProfileFragment())
                TO_CREATE_ORDER -> router.navigate(toCreateOrderFragment())
            }
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
