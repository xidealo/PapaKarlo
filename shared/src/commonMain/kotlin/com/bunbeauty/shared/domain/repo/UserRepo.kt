package com.bunbeauty.shared.domain.repo

import com.bunbeauty.core.model.profile.Profile
import com.bunbeauty.core.model.profile.User
import kotlinx.coroutines.flow.Flow

interface UserRepo {
    fun observeUserByUuid(userUuid: String): Flow<User?>

    suspend fun getProfile(): Profile.Authorized?

    suspend fun clearUserCache()

    suspend fun getToken(): String?

    suspend fun disableUser(token: String)

    fun updateNotificationToken(notificationToken: String)

    suspend fun updateNotificationTokenSuspend(notificationToken: String)
}
