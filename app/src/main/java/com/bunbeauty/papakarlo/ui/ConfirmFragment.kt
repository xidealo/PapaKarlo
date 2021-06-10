package com.bunbeauty.papakarlo.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bunbeauty.common.extensions.gone
import com.bunbeauty.common.extensions.invisible
import com.bunbeauty.common.extensions.visible
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentConfirmBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.ConfirmViewModel
import com.bunbeauty.papakarlo.ui.base.BarsFragment
import com.bunbeauty.papakarlo.ui.ConfirmFragmentArgs.fromBundle
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import kotlinx.coroutines.flow.onEach
import java.util.concurrent.TimeUnit

class ConfirmFragment : BarsFragment<FragmentConfirmBinding>() {

    override var layoutId = R.layout.fragment_confirm
    override val viewModel: ConfirmViewModel by viewModels { modelFactory }
    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    private var phoneVerificationId: String? = null
    override val isToolbarCartProductVisible = false
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sendVerificationCode(
            viewModel.getPhoneNumberDigits(fromBundle(requireArguments()).phone)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.timerStringState.onEach {
            viewDataBinding.fragmentConfirmTvResend.text = it
        }.launchWhenStarted(lifecycleScope)
        viewModel.isFinishedTimerState.onEach { isFinished ->
            if (isFinished) {
                viewDataBinding.fragmentConfirmTvResend.gone()
                viewDataBinding.fragmentConfirmBtnResend.visible()
                viewDataBinding.fragmentConfirmIvResend.visible()
            } else {
                viewDataBinding.fragmentConfirmTvResend.visible()
                viewDataBinding.fragmentConfirmBtnResend.gone()
                viewDataBinding.fragmentConfirmIvResend.gone()
            }
        }.launchWhenStarted(lifecycleScope)
        viewModel.startResendTimer()

        viewDataBinding.fragmentConfirmBtnResend.setOnClickListener {
            viewModel.startResendTimer()
            resendVerificationCode(viewModel.getPhoneNumberDigits(fromBundle(requireArguments()).phone))
        }

        viewDataBinding.fragmentConfirmTvPhoneInformation.text =
            "${viewDataBinding.fragmentConfirmTvPhoneInformation.text} ${
                fromBundle(requireArguments()).phone
            }"
        viewDataBinding.fragmentConfirmPeetCode.setOnPinEnteredListener { code ->
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
                var t = 0
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