package com.bunbeauty.shared.domain.use_case.auth

import com.bunbeauty.shared.domain.exeptions.InvalidPhoneNumberException
import com.bunbeauty.shared.domain.exeptions.SomethingWentWrongException
import com.bunbeauty.shared.domain.exeptions.TooManyRequestsException
import com.bunbeauty.shared.domain.model.CodeResponse
import com.bunbeauty.shared.domain.repo.AuthRepo

class RequestCodeUseCase(
    private val authRepo: AuthRepo,
) {

    suspend operator fun invoke(phoneNumber: String) {
        val formattedPhoneNumber = phoneNumber.replace(Regex("[ ()-]"), "")

        val phoneNumberRegex = Regex("^\\+7[0-9]{10}$")
        if (!phoneNumberRegex.matches(formattedPhoneNumber)) {
            throw InvalidPhoneNumberException()
        }

        when (authRepo.requestCode(formattedPhoneNumber)) {
            CodeResponse.SUCCESS -> Unit
            CodeResponse.TOO_MANY_REQUESTS_ERROR -> throw TooManyRequestsException()
            CodeResponse.SOMETHING_WENT_WRONG_ERROR -> throw SomethingWentWrongException()
        }
    }

}