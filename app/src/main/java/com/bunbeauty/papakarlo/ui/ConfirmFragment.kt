package com.bunbeauty.papakarlo.ui

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.papakarlo.databinding.FragmentConfirmBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.gone
import com.bunbeauty.papakarlo.extensions.invisible
import com.bunbeauty.papakarlo.extensions.startedLaunch
import com.bunbeauty.papakarlo.extensions.visible
import com.bunbeauty.papakarlo.presentation.login.ConfirmViewModel
import com.bunbeauty.papakarlo.ui.ConfirmFragmentArgs.fromBundle
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import kotlinx.coroutines.flow.onEach
import java.util.concurrent.TimeUnit

class ConfirmFragment : BaseFragment<FragmentConfirmBinding>() {

    override val viewModel: ConfirmViewModel by viewModels { modelFactory }
    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    private var phoneVerificationId: String? = null
    override val isCartVisible = false
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sendVerificationCode(
            viewModel.getPhoneNumberDigits(fromBundle(requireArguments()).phone)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(viewModel) {
            timerStringState.onEach {
                viewDataBinding.fragmentConfirmTvResend.text = it
            }.startedLaunch(viewLifecycleOwner)
            isFinishedTimerState.onEach { isFinished ->
                if (isFinished) {
                    viewDataBinding.fragmentConfirmTvResend.gone()
                    viewDataBinding.fragmentConfirmTvResendCode.visible()
                    viewDataBinding.fragmentConfirmTvResendCode.paintFlags =
                        viewDataBinding.fragmentConfirmTvResendCode.paintFlags or Paint.UNDERLINE_TEXT_FLAG

                } else {
                    viewDataBinding.fragmentConfirmTvResend.visible()
                    viewDataBinding.fragmentConfirmTvResendCode.gone()
                }
            }.startedLaunch(viewLifecycleOwner)
            startResendTimer()
        }

        with(viewDataBinding) {
            fragmentConfirmTvResendCode.setOnClickListener {
                viewModel.startResendTimer()
                resendVerificationCode(viewModel.getPhoneNumberDigits(fromBundle(requireArguments()).phone))
            }
            fragmentConfirmTvPhoneInformation.text =
                "${viewDataBinding.fragmentConfirmTvPhoneInformation.text}\n${
                    fromBundle(requireArguments()).phone
                }"
            fragmentConfirmPeetCode.setOnPinEnteredListener { code ->
                showLoading()
                phoneVerificationId?.apply {
                    val credential =
                        PhoneAuthProvider.getCredential(phoneVerificationId!!, code.toString())
                    val firebase = FirebaseAuth.getInstance()
                    firebase.signInWithCredential(credential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val userId = firebase.currentUser?.uid
                                viewModel.createUser(
                                    userId ?: "",
                                    fromBundle(requireArguments()).phone,
                                    fromBundle(requireArguments()).email
                                )
                            } else {
                                if (task.exception is FirebaseAuthInvalidCredentialsException) {
                                    hideLoading()
                                    viewModel.showCodeError()
                                }
                            }
                        }
                }
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun sendVerificationCode(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber("+$phoneNumber")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(verificationCallbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun resendVerificationCode(phoneNumber: String) {
        PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber("+$phoneNumber")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(verificationCallbacks)
            .setForceResendingToken(resendToken)
            .build()
    }

    private val verificationCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                //verifyPhoneNumberCallback.returnCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                if (e is FirebaseAuthInvalidCredentialsException) {
                    // verifyPhoneNumberCallback.returnVerificationFailed()
                } else if (e is FirebaseTooManyRequestsException) {
                    // verifyPhoneNumberCallback.returnTooManyRequestsError()
                }
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                phoneVerificationId = verificationId
                resendToken = token
                hideLoading()
            }
        }

    fun hideLoading() {
        viewDataBinding.fragmentConfirmPeetCode.visible()
        viewDataBinding.fragmentConfirmPbLoading.invisible()
    }

    private fun showLoading() {
        viewDataBinding.fragmentConfirmPeetCode.invisible()
        viewDataBinding.fragmentConfirmPbLoading.visible()
    }

}