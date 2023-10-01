package com.bunbeauty.shared.domain.repo

import com.bunbeauty.shared.domain.model.AuthResponseNew
import com.bunbeauty.shared.domain.model.CodeResponse

interface AuthRepo {

    suspend fun requestCode(phoneNumber: String): CodeResponse
    suspend fun resendCode(): CodeResponse
    suspend fun checkCode(code: String): AuthResponseNew
}