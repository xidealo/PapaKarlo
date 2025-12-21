package com.bunbeauty.shared.domain.feature.auth

import com.bunbeauty.shared.domain.exeptions.SomethingWentWrongException
import com.bunbeauty.shared.domain.repo.AuthRepo

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
