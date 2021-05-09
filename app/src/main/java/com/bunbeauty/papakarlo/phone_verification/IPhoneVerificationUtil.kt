package com.bunbeauty.papakarlo.phone_verification

import android.app.Activity
import com.bunbeauty.domain.model.AuthResult
import kotlinx.coroutines.flow.Flow

interface IPhoneVerificationUtil {

    fun sendVerificationCode(phone: String, activity: Activity)
    fun resendVerificationCode(phone: String, activity: Activity)
    fun verifyCode(code: String): Flow<AuthResult>
}