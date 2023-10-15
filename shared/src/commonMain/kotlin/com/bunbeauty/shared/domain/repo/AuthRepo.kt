package com.bunbeauty.shared.domain.repo

import com.bunbeauty.shared.domain.model.AuthResponse

interface AuthRepo {

    suspend fun requestCode(phoneNumber: String)
    suspend fun resendCode()
    suspend fun checkCode(code: String): AuthResponse?
}