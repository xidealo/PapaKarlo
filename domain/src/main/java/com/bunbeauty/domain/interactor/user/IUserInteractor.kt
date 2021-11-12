package com.bunbeauty.domain.interactor.user

import com.bunbeauty.domain.model.profile.User
import kotlinx.coroutines.flow.Flow

interface IUserInteractor {

    suspend fun refreshUser()
    fun logout()
    fun observeUser(): Flow<User?>
    suspend fun updateUserEmail(email: String): User?
}