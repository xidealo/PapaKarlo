package com.bunbeauty.papakarlo.presentation

import android.os.CountDownTimer
import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.data.model.user.User
import com.bunbeauty.data.utils.IDataStoreHelper
import com.bunbeauty.domain.repository.user.UserRepo
import com.bunbeauty.domain.resources.IResourcesProvider
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.base.ToolbarViewModel
import com.bunbeauty.papakarlo.ui.ConfirmFragmentDirections.backToProfileFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


abstract class ConfirmViewModel : ToolbarViewModel() {
    abstract val timerStringState: StateFlow<String>
    abstract val isFinishedTimerState: StateFlow<Boolean>
    abstract fun startResendTimer()
    abstract fun createUser(userId: String, phone: String, email: String)
    abstract fun getPhoneNumberDigits(phone: String): String
}

class ConfirmViewModelImpl @Inject constructor(
    private val dataStoreHelper: IDataStoreHelper,
    private val userRepo: UserRepo,
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

    override fun createUser(userId: String, phone: String, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreHelper.saveUserId(userId)
            //try to get user
            // if user not null
            //create new, else load data about user
            userRepo.getUserAsFlow(userId).onEach { user ->
                if (user == null) {
                    userRepo.insert(User(userId = userId, phone = phone, email = email))
                }else{
                    //get data for user
                    //addresses
                    //orders
                    //user
                }
                withContext(Main) {
                    router.navigate(backToProfileFragment())
                }
            }

        }
    }

    override fun getPhoneNumberDigits(phone: String): String {
        return phone.replace(Regex("\\D"), "")
    }
}