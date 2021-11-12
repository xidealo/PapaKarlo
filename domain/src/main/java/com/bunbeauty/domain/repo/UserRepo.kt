package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.Profile
import kotlinx.coroutines.flow.Flow

interface UserRepo {

    suspend fun refreshUser(userUuid: String, userPhone: String)

    suspend fun getUserByUuid(userUuid: String): Profile?

    fun observeUserByUuid(userUuid: String): Flow<Profile?>

    suspend fun updateUserEmail(profile: Profile)
}