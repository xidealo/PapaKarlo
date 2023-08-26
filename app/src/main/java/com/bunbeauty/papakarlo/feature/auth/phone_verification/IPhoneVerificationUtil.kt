package com.bunbeauty.papakarlo.feature.auth.phone_verification

import android.app.Activity
import com.bunbeauty.papakarlo.feature.auth.event.AuthErrorEvent
import com.bunbeauty.papakarlo.feature.auth.event.AuthSuccessEvent
import com.bunbeauty.papakarlo.feature.auth.event.CodeSentEvent
import kotlinx.coroutines.flow.Flow

interface IPhoneVerificationUtil {

    val authErrorEvent: Flow<AuthErrorEvent>
    val authSuccessEvent: Flow<AuthSuccessEvent>
    val codeSentEvent: Flow<CodeSentEvent>

    fun sendVerificationCode(phone: String, activity: Activity)
    fun resendVerificationCode(
        phone: String,
        activity: Activity
    )

    fun verifyCode(code: String)
}
