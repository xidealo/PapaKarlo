package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepo {

    suspend fun refreshUser()

    suspend fun getUserByUuid(userUuid: String): User?

    fun observeUserByUuid(userUuid: String): Flow<User?>

    suspend fun updateUserEmail(user: User)
}