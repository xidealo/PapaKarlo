package com.bunbeauty.core.domain.auth

import com.bunbeauty.core.domain.exeptions.SomethingWentWrongException
import com.bunbeauty.core.domain.repo.AuthRepo

class CheckCodeUseCase(
    private val authRepo: AuthRepo,
) {
    suspend operator fun invoke(code: String) {
        authRepo.checkCode(code)?.let { authResponse ->
            authRepo.saveToken(authResponse.token)
            authRepo.saveUserUuid(authResponse.userUuid)
        } ?: throw SomethingWentWrongException()
    }
}
