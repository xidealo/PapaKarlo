package com.bunbeauty.domain.auth

import kotlinx.coroutines.flow.Flow

interface IAuthUtil {

    val isAuthorize: Boolean
    val userUuid: String?
    val userPhone: String?

    fun observeUserUuid(): Flow<String?>
    fun signOut()
}