package com.bunbeauty.papakarlo.presentation.login

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.Constants.PHONE_LENGTH
import com.bunbeauty.common.Constants.TOO_MANY_REQUESTS
import com.bunbeauty.domain.interactor.user.IUserInteractor
import com.bunbeauty.domain.util.validator.ITextValidator
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.presentation.event.BaseEvent
import com.bunbeauty.papakarlo.ui.fragment.auth.LoginFragmentDirections.*
import com.bunbeauty.presentation.enums.SuccessLoginDirection
import com.bunbeauty.presentation.enums.SuccessLoginDirection.BACK_TO_PROFILE
import com.bunbeauty.presentation.enums.SuccessLoginDirection.TO_CREATE_ORDER
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val textValidator: ITextValidator,
    private val userInteractor: IUserInteractor,
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
                val phoneWithCode = PHONE_CODE + phone
                mutablePhoneCheckedEvent.send(PhoneCheckedEvent(phoneWithCode))
            }
        } else {
            mutableIsLoading.value = false
            showError(resourcesProvider.getString(R.string.error_login_phone), true)
        }
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
            TOO_MANY_REQUESTS -> {
                R.string.error_login_too_many_requests
            }
            else -> {
                R.string.error_login_something_went_wrong
            }
        }
        showError(resourcesProvider.getString(errorResourceId), true)
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

    companion object {
        private const val PHONE_CODE = "+7"
    }

    class PhoneCheckedEvent(val phone: String) : BaseEvent()
}