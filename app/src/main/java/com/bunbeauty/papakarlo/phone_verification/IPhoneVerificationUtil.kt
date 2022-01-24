package com.bunbeauty.papakarlo.phone_verification

import android.app.Activity
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.flow.Flow

interface IPhoneVerificationUtil {

    val authErrorEvent: Flow<PhoneVerificationUtil.AuthErrorEvent>
    val authSuccessEvent: Flow<PhoneVerificationUtil.AuthSuccessEvent>
    val codeSentEvent: Flow<PhoneVerificationUtil.CodeSentEvent>

    fun sendVerificationCode(phone: String, activity: Activity)
    fun resendVerificationCode(
        phone: String,
        activity: Activity,
        token: PhoneAuthProvider.ForceResendingToken
    )

    fun verifyCode(code: String, verificationId: String)
}