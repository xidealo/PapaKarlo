package com.bunbeauty.shared.domain.feature.auth

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.exeptions.SomethingWentWrongException
import com.bunbeauty.shared.domain.repo.AuthRepo

class CheckCodeUseCase(
    private val authRepo: AuthRepo,
    private val dataStoreRepo: DataStoreRepo,
) {
    suspend operator fun invoke(code: String) {
        authRepo.checkCode(code)?.let { authResponse ->
            dataStoreRepo.saveToken(authResponse.token)
            dataStoreRepo.saveUserUuid(authResponse.userUuid)
        } ?: throw SomethingWentWrongException()
    }
}
