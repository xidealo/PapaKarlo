package com.bunbeauty.papakarlo.presentation.login

import android.os.CountDownTimer
import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.UserRepo
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.di.annotation.Firebase
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class ConfirmViewModel : BaseViewModel() {
    abstract val timerStringState: StateFlow<String>
    abstract val isFinishedTimerState: StateFlow<Boolean>
    abstract fun startResendTimer()
    abstract fun showCodeError()
    abstract fun refreshUser(phone: String)
    abstract fun getPhoneNumberDigits(phone: String): String
}

class ConfirmViewModelImpl @Inject constructor(
    @Firebase private val userRepo: UserRepo,
    private val dataStoreRepo: DataStoreRepo,
    private val resourcesProvider: IResourcesProvider
) : ConfirmViewModel() {

    override val timerStringState: MutableStateFlow<String> = MutableStateFlow(
        resourcesProvider.getString(
            R.string.msg_confirm_prone_resend
        ) + " 60"
    )

    override val isFinishedTimerState: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override fun startResendTimer() {
        isFinishedTimerState.value = false
        val label = resourcesProvider.getString(R.string.msg_confirm_prone_resend)
        object : CountDownTimer(60 * 1000L, 1000L) {
            override fun onFinish() {
                isFinishedTimerState.value = true
            }

            override fun onTick(millisUntilFinished: Long) {
                timerStringState.value = "$label ${(millisUntilFinished / 1000)}"
            }
        }.start()
    }

    override fun refreshUser(phone: String) {
        viewModelScope.launch {
            dataStoreRepo.clearUserAddressUuid()
            userRepo.refreshUser()
            goBack()
            goBack()
        }
    }

    override fun showCodeError() {
        showError(resourcesProvider.getString(R.string.msg_confirm_prone_error_code))
    }

    override fun getPhoneNumberDigits(phone: String): String {
        return phone.replace(Regex("\\D"), "")
    }
}