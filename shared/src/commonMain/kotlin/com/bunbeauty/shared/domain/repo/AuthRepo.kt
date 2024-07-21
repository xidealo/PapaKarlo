package com.bunbeauty.shared.domain.repo

import com.bunbeauty.shared.domain.model.AuthResponse

interface AuthRepo {

    suspend fun requestCode(phoneNumber: String): Boolean
    suspend fun resendCode(): Boolean
    suspend fun checkCode(code: String): AuthResponse?
}
