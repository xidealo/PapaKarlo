package com.bunbeauty.papakarlo.presentation.login

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.Constants.PHONE_LENGTH
import com.bunbeauty.common.Constants.TOO_MANY_REQUESTS
import com.bunbeauty.domain.repo.UserRepo
import com.bunbeauty.domain.util.validator.ITextValidator
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.presentation.event.BaseEvent
import com.bunbeauty.papakarlo.ui.fragment.auth.LoginFragmentDirections.toConfirmFragment
import com.bunbeauty.presentation.enums.SuccessLoginDirection
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import com.example.data_api.Api
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    @Api private val userRepo: UserRepo,
    private val textValidator: ITextValidator,
    private val resourcesProvider: IResourcesProvider
) : BaseViewModel() {

    private val mutableIsLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = mutableIsLoading.asStateFlow()

    private val mutablePhoneCheckedEvent: Channel<PhoneCheckedEvent> = Channel()
    val phoneCheckedEvent: Flow<PhoneCheckedEvent> = mutablePhoneCheckedEvent.receiveAsFlow()

    fun onViewCreated() {
        mutableIsLoading.value = false
    }

    fun onNextClicked(phone: String) {
        mutableIsLoading.value = true
        if (textValidator.isFieldContentCorrect(phone, PHONE_LENGTH, PHONE_LENGTH)) {
            viewModelScope.launch {
                mutablePhoneCheckedEvent.send(PhoneCheckedEvent(phone))
            }
        } else {
            mutableIsLoading.value = false
            showError(resourcesProvider.getString(R.string.error_login_phone))
        }
    }

    fun onSuccessVerified() {
        viewModelScope.launch {
            userRepo.refreshUser()
            goBack()
        }
    }

    fun onVerificationError(error: String) {
        mutableIsLoading.value = false
        val errorResourceId = when (error) {
            TOO_MANY_REQUESTS -> {
                R.string.error_login_too_many_requests
            }
            else -> {
                R.string.error_login_something_went_wrong
            }
        }
        showError(resourcesProvider.getString(errorResourceId))
    }

    fun onCodeSent(
        phone: String,
        verificationId: String,
        resendToken: PhoneAuthProvider.ForceResendingToken,
        successLoginDirection: SuccessLoginDirection
    ) {
        router.navigate(
            toConfirmFragment(
                phone,
                verificationId,
                resendToken,
                successLoginDirection
            )
        )
    }

    class PhoneCheckedEvent(val phone: String) : BaseEvent()
}