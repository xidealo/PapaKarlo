package com.bunbeauty.core.domain.auth

import com.bunbeauty.core.domain.exeptions.SomethingWentWrongException
import com.bunbeauty.core.domain.repo.AuthRepo

class ResendCodeUseCase(
    private val authRepo: AuthRepo,
) {
    suspend operator fun invoke() {
        val isSuccess = authRepo.resendCode()
        if (!isSuccess) {
            throw SomethingWentWrongException()
        }
    }
}
