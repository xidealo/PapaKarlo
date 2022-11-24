package com.bunbeauty.papakarlo.feature.auth.event

import com.google.firebase.auth.PhoneAuthProvider

class CodeSentEvent(
    val phone: String,
    val verificationId: String,
    val token: PhoneAuthProvider.ForceResendingToken
)
