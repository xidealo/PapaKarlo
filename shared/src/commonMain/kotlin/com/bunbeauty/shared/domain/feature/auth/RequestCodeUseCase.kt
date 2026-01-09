package com.bunbeauty.shared.domain.feature.auth

import com.bunbeauty.core.domain.exeptions.SomethingWentWrongException
import com.bunbeauty.core.domain.repo.AuthRepo

class RequestCodeUseCase(
    private val authRepo: AuthRepo,
) {
    suspend operator fun invoke(phoneNumber: String) {
        val isSuccess = authRepo.requestCode(phoneNumber)
        if (!isSuccess) {
            throw SomethingWentWrongException()
        }
    }
}
