package com.bunbeauty.shared.domain.use_case

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.repo.UserRepo

class DisableUserUseCase(
    private val userRepo: UserRepo,
    private val dataStoreRepo: DataStoreRepo
) {
    suspend fun invoke() {
        dataStoreRepo.getToken()?.let { token ->
            userRepo.disableUser(token = token)
        }
    }
}