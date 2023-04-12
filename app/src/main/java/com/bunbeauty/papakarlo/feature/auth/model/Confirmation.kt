package com.bunbeauty.papakarlo.feature.auth.model

import com.google.firebase.auth.PhoneAuthProvider

data class Confirmation(
    val phoneNumber: String,
    val resendToken: PhoneAuthProvider.ForceResendingToken,
    val verificationId: String,
    val resendSeconds: Int,
    val isCodeChecking: Boolean,
) {
    val isResendEnable: Boolean = resendSeconds == 0
    val formattedPhoneNumber: String = phoneNumber.replace(Regex("[\\s()-]"), "")
}
