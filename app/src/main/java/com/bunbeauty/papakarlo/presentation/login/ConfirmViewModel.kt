package com.bunbeauty.papakarlo.presentation.login

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.Logger.AUTH_TAG
import com.bunbeauty.common.Logger.logD
import com.bunbeauty.domain.model.AuthResult
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.UserRepo
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.di.annotation.Api
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class ConfirmViewModel @Inject constructor(
    @Api private val userRepo: UserRepo,
    private val dataStoreRepo: DataStoreRepo,
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

    fun onCodeEntered(verifyCodeFlow: Flow<AuthResult>) {
        mutableIsLoading.value = true
        verifyCodeFlow.onEach { authResult ->
            when (authResult) {
                is AuthResult.AuthSuccess -> {
                    refreshUser()
                }
                is AuthResult.AuthError -> {
                    mutableIsLoading.value = false
                    showError(resourcesProvider.getString(R.string.msg_confirm_error_code))
                    logD(AUTH_TAG, authResult.errorMessage)
                }
            }
        }.launchIn(viewModelScope)
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

    private fun refreshUser() {
        viewModelScope.launch {
            dataStoreRepo.clearUserAddressUuid()
            userRepo.refreshUser()
            goBack()
            goBack()
        }
    }
}