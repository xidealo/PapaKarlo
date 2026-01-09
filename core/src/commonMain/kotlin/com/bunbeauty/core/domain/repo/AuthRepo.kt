package com.bunbeauty.core.domain.repo

import com.bunbeauty.core.model.AuthResponse

interface AuthRepo {
    suspend fun requestCode(phoneNumber: String): Boolean

    suspend fun resendCode(): Boolean
    suspend fun saveToken(token: String)
    suspend fun saveUserUuid(userUuid: String)

    suspend fun checkCode(code: String): AuthResponse?
}
