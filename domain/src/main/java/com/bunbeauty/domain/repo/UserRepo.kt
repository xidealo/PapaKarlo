package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.profile.Profile
import com.bunbeauty.domain.model.profile.User
import kotlinx.coroutines.flow.Flow

interface UserRepo {

    suspend fun login(userUuid: String, userPhone: String): String?

    suspend fun refreshProfile(token: String)

    fun observeUserByUuid(userUuid: String): Flow<User?>

    fun observeProfileByUserUuidAndCityUuid(userUuid: String, cityUuid: String): Flow<Profile?>

    suspend fun updateUserEmail(token: String, userUuid: String, email: String): User?
}