package com.bunbeauty.core.domain.auth

import com.bunbeauty.core.domain.repo.UserRepo
import kotlinx.coroutines.flow.Flow

class ObserveTokenUseCase(
    private val userRepo: UserRepo,
) {
    operator fun invoke(): Flow<String?> = userRepo.token
}
