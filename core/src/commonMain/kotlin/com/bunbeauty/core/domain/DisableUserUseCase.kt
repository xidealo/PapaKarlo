package com.bunbeauty.core.domain

import com.bunbeauty.core.domain.repo.UserRepo

class DisableUserUseCase(
    private val userRepo: UserRepo,
) {
    suspend operator fun invoke() {
        userRepo.disableUser()
    }
}