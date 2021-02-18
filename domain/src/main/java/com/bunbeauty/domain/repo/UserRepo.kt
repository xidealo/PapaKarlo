package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.profile.Profile
import com.bunbeauty.domain.model.profile.User
import kotlinx.coroutines.flow.Flow

interface UserRepo {

    suspend fun login(userUuid: String, userPhone: String): String?

    suspend fun refreshUser(token: String)

    suspend fun getUserByUuid(userUuid: String): Profile?

    fun observeProfileByUuid(userUuid: String): Flow<Profile?>

    suspend fun updateUserEmail(user: User): User?
}