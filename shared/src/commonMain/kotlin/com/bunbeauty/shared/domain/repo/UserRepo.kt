package com.bunbeauty.shared.domain.repo

import com.bunbeauty.shared.domain.model.AuthResponse
import com.bunbeauty.shared.domain.model.profile.Profile
import com.bunbeauty.shared.domain.model.profile.User
import kotlinx.coroutines.flow.Flow

interface UserRepo {

    suspend fun login(userUuid: String, userPhone: String): AuthResponse?

    fun observeUserByUuid(userUuid: String): Flow<User?>

    suspend fun getProfileByUserUuidAndCityUuid(
        userUuid: String,
        cityUuid: String,
        token: String
    ): Profile.Authorized?

    suspend fun clearUserCache()

    suspend fun disableUser(token: String)
}