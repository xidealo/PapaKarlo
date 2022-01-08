package com.bunbeauty.domain.interactor.user

import com.bunbeauty.domain.model.profile.LightProfile
import com.bunbeauty.domain.model.profile.User
import kotlinx.coroutines.flow.Flow

interface IUserInteractor {

    suspend fun login()
    suspend fun logout()
    suspend fun isUserAuthorize(): Boolean

    fun observeIsUserAuthorize(): Flow<Boolean>
    fun observeUser(): Flow<User?>
    fun observeLightProfile(): Flow<LightProfile?>
    suspend fun updateUserEmail(email: String): User?
}