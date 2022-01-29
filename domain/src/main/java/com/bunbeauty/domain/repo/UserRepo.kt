package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.profile.LightProfile
import com.bunbeauty.domain.model.profile.Profile
import com.bunbeauty.domain.model.profile.User
import kotlinx.coroutines.flow.Flow

interface UserRepo {

    suspend fun login(userUuid: String, userPhone: String): String?

    suspend fun refreshUser(token: String)

    suspend fun getUserByUuid(userUuid: String): Profile?

    fun observeUserByUuid(userUuid: String): Flow<User?>

    fun observeProfileByUuid(userUuid: String): Flow<LightProfile?>

    suspend fun updateUserEmail(token: String, userUuid: String, email: String): User?
}