package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.Profile
import kotlinx.coroutines.flow.Flow

interface UserRepo {

    suspend fun refreshUser()

    suspend fun getUserByUuid(userUuid: String): Profile?

    fun observeUserByUuid(userUuid: String): Flow<Profile?>

    suspend fun updateUserEmail(profile: Profile)
}