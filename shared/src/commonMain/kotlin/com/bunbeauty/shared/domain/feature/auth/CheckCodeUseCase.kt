package com.bunbeauty.shared.domain.feature.auth

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.exeptions.AuthSessionTimeoutException
import com.bunbeauty.shared.domain.exeptions.InvalidCodeException
import com.bunbeauty.shared.domain.exeptions.NoAttemptsException
import com.bunbeauty.shared.domain.exeptions.SomethingWentWrongException
import com.bunbeauty.shared.domain.model.AuthResponseNew
import com.bunbeauty.shared.domain.repo.AuthRepo

class CheckCodeUseCase(
    private val authRepo: AuthRepo,
    private val dataStoreRepo: DataStoreRepo,
) {

    suspend operator fun invoke(code: String) {
        when (val authResponse = authRepo.checkCode(code)) {
            is AuthResponseNew.Success -> {
                dataStoreRepo.saveToken(authResponse.token)
                dataStoreRepo.saveUserUuid(authResponse.userUuid)
            }
            AuthResponseNew.Error.NO_ATTEMPTS_ERROR -> throw NoAttemptsException()
            AuthResponseNew.Error.INVALID_CODE_ERROR -> throw InvalidCodeException()
            AuthResponseNew.Error.AUTH_SESSION_TIMEOUT_ERROR -> throw AuthSessionTimeoutException()
            AuthResponseNew.Error.SOMETHING_WENT_WRONG_ERROR -> throw SomethingWentWrongException()
        }
    }

}