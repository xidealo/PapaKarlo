package com.bunbeauty.papakarlo.feature.auth.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import core_common.Constants.PHONE_CODE
import core_common.Constants.TOO_MANY_REQUESTS
import com.bunbeauty.domain.interactor.user.IUserInteractor
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.enums.SuccessLoginDirection
import com.bunbeauty.papakarlo.enums.SuccessLoginDirection.BACK_TO_PROFILE
import com.bunbeauty.papakarlo.enums.SuccessLoginDirection.TO_CREATE_ORDER
import com.bunbeauty.papakarlo.feature.auth.login.LoginFragmentDirections.*
import com.bunbeauty.papakarlo.util.resources.IResourcesProvider
import com.bunbeauty.papakarlo.util.text_validator.ITextValidator
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val textValidator: ITextValidator,
    private val userInteractor: IUserInteractor,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val mutableIsLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = mutableIsLoading.asStateFlow()

    private val successLoginDirection: SuccessLoginDirection by lazy {
        savedStateHandle["successLoginDirection"] ?: BACK_TO_PROFILE
    }

    fun setNotLoading() {
        mutableIsLoading.value = false
    }

    fun formatPhoneNumber(inputPhoneNumber: String): String {
        val numbers = inputPhoneNumber.run {
            if (!contains(PHONE_CODE) && isNotEmpty()) {
                ""
            } else {
                this
            }
        }.replace(PHONE_CODE, "")
            .replace(Regex("\\D"), "")
        val firstGroup = numbers.take(3)
        val secondGroup = numbers.drop(3).take(3)
        val thirdGroup = numbers.drop(6).take(2)
        val fourthGroup = numbers.drop(8).take(2)
        var result = PHONE_CODE
        if (firstGroup.isNotEmpty()) {
            result += " ($firstGroup"
        }
        if (secondGroup.isNotEmpty()) {
            result += ") $secondGroup"
        }
        if (thirdGroup.isNotEmpty()) {
            result += "-$thirdGroup"
        }
        if (fourthGroup.isNotEmpty()) {
            result += "-$fourthGroup"
        }

        return result
    }

    fun getNewPosition(inputPhoneNumber: String, cursorPosition: Int): Int {
        var newPosition = cursorPosition
        when (cursorPosition) {
            0, 1 -> {
                newPosition = 2
            }
            3 -> {
                // "+70" -> "+7 (0"
                if (inputPhoneNumber[2].isNumber()) {
                    newPosition = 5
                }
            }
            8 -> {
                // "+7 (0000" -> "+7 (000) 0"
                if (inputPhoneNumber[7].isNumber()) {
                    newPosition = 10
                }
            }
            13 -> {
                // "+7 (000) 0000" -> "+7 (000) 000-0"
                if (inputPhoneNumber[12].isNumber()) {
                    newPosition = 14
                }
            }
            16 -> {
                // "+7 (000) 000-000" -> "+7 (000) 000-00-0"
                if (inputPhoneNumber[15].isNumber()) {
                    newPosition = 17
                }
            }
        }

        val phoneNumber = formatPhoneNumber(inputPhoneNumber)
        return if (newPosition > phoneNumber.length) {
            phoneNumber.length
        } else {
            newPosition
        }
    }

    fun checkPhoneNumberError(inputPhoneNumber: String): Int? {
        return if (textValidator.isPhoneNumberCorrect(inputPhoneNumber)) {
            null
        } else {
            R.string.error_login_phone
        }
    }

    fun onNextClick() {
        mutableIsLoading.value = true
    }

    fun onSuccessVerified() {
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
        val errorResId = when (error) {
            TOO_MANY_REQUESTS -> {
                R.string.error_login_too_many_requests
            }
            else -> {
                R.string.error_login_something_try_later
            }
        }
        showError(resourcesProvider.getString(errorResId), true)
    }

    fun onCodeSent(
        phone: String,
        verificationId: String,
        resendToken: PhoneAuthProvider.ForceResendingToken
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

    private fun Char.isNumber(): Boolean {
        return Regex("\\d").matches(this.toString())
    }
}