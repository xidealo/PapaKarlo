package com.bunbeauty.shared.domain.interactor.user

import com.bunbeauty.shared.domain.CommonFlow
import com.bunbeauty.shared.domain.model.profile.Profile
import com.bunbeauty.shared.domain.model.profile.User
import kotlinx.coroutines.flow.Flow

interface IUserInteractor {

    @Throws(Exception::class)
    suspend fun login(firebaseUserUuid: String?, firebaseUserPhone: String?)

    suspend fun clearUserCache()
    suspend fun isUserAuthorize(): Boolean

    fun observeIsUserAuthorize(): CommonFlow<Boolean>
    fun observeUser(): Flow<User?>
    suspend fun getProfile(): Profile?
}