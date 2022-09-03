package com.bunbeauty.shared.domain.interactor.user

import com.bunbeauty.shared.domain.model.profile.Profile
import com.bunbeauty.shared.domain.model.profile.User
import kotlinx.coroutines.flow.Flow

interface IUserInteractor {
    suspend fun login(firebaseUserUuid: String?, firebaseUserPhone: String?)
    suspend fun clearUserCache()
    suspend fun isUserAuthorize(): Boolean

    fun observeIsUserAuthorize(): Flow<Boolean>
    fun observeUser(): Flow<User?>
    suspend fun getProfile(): Profile?
    suspend fun updateUserEmail(email: String): User?
}