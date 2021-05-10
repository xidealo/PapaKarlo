package com.bunbeauty.papakarlo.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.common.extensions.invisible
import com.bunbeauty.common.extensions.visible
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentConfirmBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.ConfirmViewModel
import com.bunbeauty.papakarlo.ui.base.BarsFragment
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class ConfirmFragment : BarsFragment<FragmentConfirmBinding>() {

    override var layoutId = R.layout.fragment_confirm
    override val viewModel: ConfirmViewModel by viewModels { modelFactory }
    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    private var phoneVerificationId: String? = null
    override val isToolbarCartProductVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sendVerificationCode(
            viewModel.getPhoneNumberDigits(ConfirmFragmentArgs.fromBundle(requireArguments()).phone)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.fragmentConfirmTvPhoneInformation.text =
            "${viewDataBinding.fragmentConfirmTvPhoneInformation.text} ${
                ConfirmFragmentArgs.fromBundle(requireArguments()).phone
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
                                ConfirmFragmentArgs.fromBundle(requireArguments()).phone,
                                ConfirmFragmentArgs.fromBundle(requireArguments()).email
                            )
                        } else {
                            if (task.exception is FirebaseAuthInvalidCredentialsException) {
                                hideLoading()
                            }
                        }
                    }
            }
        }
    }

    fun sendVerificationCode(
        phoneNumber: String
    ) {
        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber("+$phoneNumber") // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity()) // Activity (for callback binding)
            .setCallbacks(verificationCallbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
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
                hideLoading()
            }
        }

    fun hideLoading() {
        viewDataBinding.fragmentConfirmPeetCode.visible()
        viewDataBinding.fragmentConfirmPbLoading.invisible()
    }

    fun showLoading() {
        viewDataBinding.fragmentConfirmPeetCode.invisible()
        viewDataBinding.fragmentConfirmPbLoading.visible()
    }

}