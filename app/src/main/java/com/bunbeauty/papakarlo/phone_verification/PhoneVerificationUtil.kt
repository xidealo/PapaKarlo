package com.bunbeauty.papakarlo.phone_verification

import android.app.Activity
import com.bunbeauty.domain.model.AuthResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import com.google.firebase.auth.AuthResult as Result

class PhoneVerificationUtil @Inject constructor() : IPhoneVerificationUtil {

    val timeout = 60L
    var resendToken: PhoneAuthProvider.ForceResendingToken? = null
    var phoneVerificationId: String? = null
    val verificationCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            //verifyPhoneNumberCallback.returnCredential(credential)
        }

        override fun onVerificationFailed(exception: FirebaseException) {
            if (exception is FirebaseAuthInvalidCredentialsException) {
                // verifyPhoneNumberCallback.returnVerificationFailed()
            } else if (exception is FirebaseTooManyRequestsException) {
                // verifyPhoneNumberCallback.returnTooManyRequestsError()
            }
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            phoneVerificationId = verificationId
            resendToken = token
        }
    }

    override fun sendVerificationCode(phone: String, activity: Activity) {
        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber(phone)
            .setActivity(activity)
            .setTimeout(timeout, TimeUnit.SECONDS)
            .setCallbacks(verificationCallbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun resendVerificationCode(phone: String, activity: Activity) {
        resendToken?.let { resendToken ->
            PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                .setPhoneNumber(phone)
                .setActivity(activity)
                .setTimeout(timeout, TimeUnit.SECONDS)
                .setCallbacks(verificationCallbacks)
                .setForceResendingToken(resendToken)
                .build()
        }
    }

    override fun verifyCode(code: String): Flow<AuthResult> = callbackFlow {
        var onCompleteListener: OnCompleteListener<Result>? =
            OnCompleteListener<Result> { task ->
                if (task.isSuccessful) {
                    trySend(AuthResult.AuthSuccess)
                } else {
                    trySend(AuthResult.AuthError(task.exception?.message ?: ""))
                }
            }
        phoneVerificationId?.let { verificationId ->
            val credential = PhoneAuthProvider.getCredential(verificationId, code)
            val signInTask = FirebaseAuth.getInstance().signInWithCredential(credential)

            onCompleteListener?.let { listener ->
                signInTask.addOnCompleteListener(listener)
            }
        }
        awaitClose {
            onCompleteListener = null
        }
    }
}