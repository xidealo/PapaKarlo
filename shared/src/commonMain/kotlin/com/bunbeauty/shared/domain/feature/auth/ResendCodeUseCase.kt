package com.bunbeauty.shared.domain.feature.auth

import com.bunbeauty.shared.domain.exeptions.SomethingWentWrongException
import com.bunbeauty.shared.domain.repo.AuthRepo

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
