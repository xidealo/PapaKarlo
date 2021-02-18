package com.bunbeauty.domain.interactor.user

import com.bunbeauty.domain.model.profile.Profile
import com.bunbeauty.domain.model.profile.User
import kotlinx.coroutines.flow.Flow

interface IUserInteractor {

    suspend fun login()
    suspend fun logout()
    fun observeUser(): Flow<User?>
    fun observeProfile(): Flow<Profile?>
    suspend fun updateUserEmail(email: String): User?
}