package com.bunbeauty.papakarlo.presentation.login

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.Constants.WRONG_CODE
import com.bunbeauty.common.Logger.AUTH_TAG
import com.bunbeauty.common.Logger.logD
import com.bunbeauty.domain.interactor.user.IUserInteractor
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.fragment.auth.ConfirmFragmentDirections.backToProfileFragment
import com.bunbeauty.papakarlo.ui.fragment.auth.ConfirmFragmentDirections.toCreateOrderFragment
import com.bunbeauty.presentation.enums.SuccessLoginDirection
import com.bunbeauty.presentation.enums.SuccessLoginDirection.BACK_TO_PROFILE
import com.bunbeauty.presentation.enums.SuccessLoginDirection.TO_CREATE_ORDER
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ConfirmViewModel @Inject constructor(
    private val userInteractor: IUserInteractor,
    private val resourcesProvider: IResourcesProvider
) : BaseViewModel() {

    private val label = resourcesProvider.getString(R.string.msg_confirm_code_info)
    private val seconds = resourcesProvider.getString(R.string.msg_confirm_seconds)
    private val timerSecondCount = 60
    private val timerIntervalMillis = 1000L

    private val mutableResendSecondsInfo: MutableStateFlow<String> =
        MutableStateFlow(label + timerSecondCount + seconds)
    val resendSecondsInfo: StateFlow<String> = mutableResendSecondsInfo.asStateFlow()

    private val mutableIsTimerRun: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isTimerRun: StateFlow<Boolean> = mutableIsTimerRun.asStateFlow()

    private val mutableIsLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = mutableIsLoading.asStateFlow()

    fun onCodeEntered() {
        mutableIsLoading.value = true
    }

    fun onSuccessVerified(successLoginDirection: SuccessLoginDirection) {
        viewModelScope.launch {
            userInteractor.login()

            when (successLoginDirection) {
                BACK_TO_PROFILE -> router.navigate(backToProfileFragment())
                TO_CREATE_ORDER -> router.navigate(toCreateOrderFragment())
            }
        }
    }

    fun onVerificationError(error: String) {
        mutableIsLoading.value = false
        val errorResourceId = when (error) {
            WRONG_CODE -> {
                R.string.error_confirm_wrong_code
            }
            else -> {
                R.string.error_confirm_something_went_wrong
            }
        }
        showError(resourcesProvider.getString(errorResourceId))
        logD(AUTH_TAG, error)
    }

    fun getPhoneInfo(phone: String): String {
        return resourcesProvider.getString(R.string.msg_confirm_phone_info) + phone
    }

    fun onChangePhoneClicked() {
        goBack()
    }

    // Remove ' ', '(', ')', '-' symbols from "+X (XXX) XXX-XX-XX" phone format
    fun formatPhone(phone: String): String {
        return phone.replace(Regex("[\\s()-]"), "")
    }

    fun startResendTimer() {
        if (mutableIsTimerRun.value) {
            return
        }
        mutableIsTimerRun.value = true

        var secondCount = timerSecondCount
        viewModelScope.launch {
            while (secondCount > 0) {
                delay(timerIntervalMillis)
                secondCount--
                mutableResendSecondsInfo.value = label + secondCount + seconds
            }
            mutableIsTimerRun.value = false
            mutableResendSecondsInfo.value = label + timerSecondCount + seconds
        }
    }
}