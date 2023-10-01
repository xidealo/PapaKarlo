package com.bunbeauty.shared.domain.use_case.auth

import com.bunbeauty.shared.domain.exeptions.SomethingWentWrongException
import com.bunbeauty.shared.domain.exeptions.TooManyRequestsException
import com.bunbeauty.shared.domain.model.CodeResponse
import com.bunbeauty.shared.domain.repo.AuthRepo

class ResendCodeUseCase(
    private val authRepo: AuthRepo,
) {

    suspend operator fun invoke() {
        when (authRepo.resendCode()) {
            CodeResponse.SUCCESS -> Unit
            CodeResponse.TOO_MANY_REQUESTS_ERROR -> throw TooManyRequestsException()
            CodeResponse.SOMETHING_WENT_WRONG_ERROR -> throw SomethingWentWrongException()
        }
    }
}