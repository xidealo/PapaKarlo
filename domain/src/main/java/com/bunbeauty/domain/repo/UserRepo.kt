package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.profile.Profile
import com.bunbeauty.domain.model.profile.User
import kotlinx.coroutines.flow.Flow

interface UserRepo {

    suspend fun login(userUuid: String, userPhone: String): String?

    fun observeUserByUuid(userUuid: String): Flow<User?>

    suspend fun getProfileByUserUuidAndCityUuid(
        userUuid: String,
        cityUuid: String,
        token: String
    ): Profile.Authorized?

    suspend fun updateUserEmail(token: String, userUuid: String, email: String): User?

    suspend fun clearUserCache()

}