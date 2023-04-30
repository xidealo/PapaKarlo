package com.bunbeauty.papakarlo.feature.auth.phone_verification

import android.app.Activity
import com.bunbeauty.papakarlo.feature.auth.event.AuthErrorEvent
import com.bunbeauty.papakarlo.feature.auth.event.AuthSuccessEvent
import com.bunbeauty.papakarlo.feature.auth.event.CodeSentEvent
import com.bunbeauty.shared.Constants.SOMETHING_WENT_WRONG
import com.bunbeauty.shared.Constants.TOO_MANY_REQUESTS
import com.bunbeauty.shared.Constants.WRONG_CODE
import com.bunbeauty.shared.Logger.AUTH_TAG
import com.bunbeauty.shared.Logger.logD
import com.bunbeauty.shared.Logger.logE
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

class PhoneVerificationUtil : IPhoneVerificationUtil, CoroutineScope {

    private val sendCodeTimeout = 10_000L
    private val autoRetrievalTimeout = 60L

    override val coroutineContext: CoroutineContext
        get() = Job()

    private val mutableAuthErrorEvent: MutableSharedFlow<AuthErrorEvent> = MutableSharedFlow()
    override val authErrorEvent: SharedFlow<AuthErrorEvent> = mutableAuthErrorEvent.asSharedFlow()

    private val mutableAuthSuccessEvent: MutableSharedFlow<AuthSuccessEvent> = MutableSharedFlow()
    override val authSuccessEvent: SharedFlow<AuthSuccessEvent> =
        mutableAuthSuccessEvent.asSharedFlow()

    private val mutableCodeSentEvent: MutableSharedFlow<CodeSentEvent> = MutableSharedFlow()
    override val codeSentEvent: SharedFlow<CodeSentEvent> = mutableCodeSentEvent.asSharedFlow()

    private var verificationId: String? = null
    private var forceResendingToken: PhoneAuthProvider.ForceResendingToken? = null

    override fun sendVerificationCode(phone: String, activity: Activity) {
        verifyPhoneNumber(phone, activity)
    }

    override fun resendVerificationCode(phone: String, activity: Activity) {
        forceResendingToken?.let { token ->
            verifyPhoneNumber(phone, activity, token)
        }
    }

    override fun verifyCode(code: String) {
        verificationId?.let { id ->
            val credential = PhoneAuthProvider.getCredential(id, code)
            signInWithCredential(credential)
        }
    }

    fun verifyPhoneNumber(
        phone: String,
        activity: Activity,
        token: PhoneAuthProvider.ForceResendingToken? = null
    ) {
        val timeoutJob: Job = launch {
            delay(sendCodeTimeout)
            sendError(SOMETHING_WENT_WRONG)
            logE(AUTH_TAG, "sendCodeTimeout")
        }
        val verificationCallback =
            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    timeoutJob.cancel()
                    signInWithCredential(credential)
                }

                override fun onVerificationFailed(exception: FirebaseException) {
                    timeoutJob.cancel()
                    when (exception) {
                        is FirebaseTooManyRequestsException -> {
                            sendError(TOO_MANY_REQUESTS)
                        }
                        else -> {
                            sendError(SOMETHING_WENT_WRONG)
                        }
                    }
                    logE(AUTH_TAG, "onVerificationFailed exception " + exception.message)
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    timeoutJob.cancel()
                    sendCodeSent(phone, verificationId, token)
                    logD(AUTH_TAG, "onCodeSent")
                }
            }

        val options = PhoneAuthOptions.newBuilder(Firebase.auth)
            .setPhoneNumber(phone)
            .setActivity(activity)
            .setTimeout(autoRetrievalTimeout, TimeUnit.SECONDS)
            .setCallbacks(verificationCallback)
        if (token != null) {
            options.setForceResendingToken(token)
        }
        PhoneAuthProvider.verifyPhoneNumber(options.build())
    }

    fun signInWithCredential(credential: PhoneAuthCredential) {
        logD(AUTH_TAG, "signInWithCredential")
        Firebase.auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                sendSuccess()
                logD(AUTH_TAG, "signInWithCredential Successful")
            } else {
                sendError(WRONG_CODE)
                logE(AUTH_TAG, task.exception?.message ?: WRONG_CODE)
            }
        }
    }

    fun sendError(error: String) {
        launch {
            mutableAuthErrorEvent.emit(AuthErrorEvent(error))
        }
    }

    fun sendSuccess() {
        launch {
            mutableAuthSuccessEvent.emit(AuthSuccessEvent())
        }
    }

    fun sendCodeSent(
        phone: String,
        verificationId: String,
        forceResendingToken: PhoneAuthProvider.ForceResendingToken
    ) {
        this.verificationId = verificationId
        this.forceResendingToken = forceResendingToken
        launch {
            mutableCodeSentEvent.emit(CodeSentEvent(phone))
        }
    }
}
