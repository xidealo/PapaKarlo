package com.bunbeauty.domain.interactor.user

import com.bunbeauty.shared.domain.model.profile.Profile
import com.bunbeauty.shared.domain.model.profile.User
import kotlinx.coroutines.flow.Flow

interface IUserInteractor {

    suspend fun login()
    suspend fun logout()
    suspend fun isUserAuthorize(): Boolean

    fun observeIsUserAuthorize(): Flow<Boolean>
    fun observeUser(): Flow<User?>
    suspend fun getProfile(): Profile?
    suspend fun updateUserEmail(email: String): User?
}