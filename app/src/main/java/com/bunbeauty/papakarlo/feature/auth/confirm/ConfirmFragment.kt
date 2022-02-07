package com.bunbeauty.papakarlo.feature.auth.confirm

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.delegates.argument
import com.bunbeauty.papakarlo.databinding.FragmentConfirmBinding
import com.bunbeauty.papakarlo.enums.SuccessLoginDirection
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.extensions.toggleVisibilityInvisibility
import com.bunbeauty.papakarlo.extensions.underlineText
import com.bunbeauty.papakarlo.feature.auth.phone_verification.IPhoneVerificationUtil
import com.google.firebase.auth.PhoneAuthProvider
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConfirmFragment : BaseFragment(R.layout.fragment_confirm) {

    val phoneVerificationUtil: IPhoneVerificationUtil by inject()

    override val viewModel: ConfirmViewModel by viewModel()
    override val viewBinding by viewBinding(FragmentConfirmBinding::bind)

    private val phone: String by argument()
    private var verificationId: String by argument()
    private var resendToken: PhoneAuthProvider.ForceResendingToken by argument()
    private var successLoginDirection: SuccessLoginDirection by argument()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.startResendTimer()
        viewBinding.run {
            fragmentConfirmTvPhoneInformation.text = viewModel.getPhoneInfo(phone)
            fragmentConfirmEtCode.focus()
            fragmentConfirmEtCode.setOnPinEnteredListener { code ->
                viewModel.onCodeEntered()
                phoneVerificationUtil.verifyCode(
                    code = code.toString(),
                    verificationId = verificationId
                )
            }
            fragmentConfirmTvResendCode.underlineText()
            fragmentConfirmTvResendCode.setOnClickListener {
                viewModel.startResendTimer()
                phoneVerificationUtil.resendVerificationCode(
                    phone = viewModel.formatPhone(phone),
                    activity = requireActivity(),
                    token = resendToken
                )
            }
            fragmentConfirmTvChangePhone.underlineText()
            fragmentConfirmLlChangePhone.setOnClickListener {
                viewModel.onChangePhoneClicked()
            }

            viewModel.resendSecondsInfo.startedLaunch { resendSecondsInfo ->
                fragmentConfirmTvResendSecondsInfo.text = resendSecondsInfo
            }
            viewModel.isTimerRun.startedLaunch { isTimerRun ->
                fragmentConfirmTvResendSecondsInfo.toggleVisibility(isTimerRun && !viewModel.isLoading.value)
                fragmentConfirmTvResendCode.toggleVisibility(!isTimerRun && !viewModel.isLoading.value)
            }
            viewModel.isLoading.startedLaunch { isLoading ->
                if (isLoading) {
                    hideKeyboard()
                    fragmentConfirmEtCode.clearFocus()
                }
                viewBinding.fragmentConfirmPbLoading.toggleVisibility(isLoading)
                viewBinding.fragmentConfirmEtCode.toggleVisibilityInvisibility(!isLoading)
                fragmentConfirmTvResendSecondsInfo.toggleVisibility(viewModel.isTimerRun.value && !isLoading)
                fragmentConfirmTvResendCode.toggleVisibility(!viewModel.isTimerRun.value && !isLoading)
                if (!isLoading) {
                    fragmentConfirmEtCode.setText("")
                    fragmentConfirmEtCode.focus()
                }
            }

            phoneVerificationUtil.codeSentEvent.startedLaunch { codeSentEvent ->
                verificationId = codeSentEvent.verificationId
                resendToken = codeSentEvent.token
            }
            phoneVerificationUtil.authErrorEvent.startedLaunch { authErrorEvent ->
                viewModel.onVerificationError(authErrorEvent.error)
            }
            phoneVerificationUtil.authSuccessEvent.startedLaunch {
                viewModel.onSuccessVerified(successLoginDirection)
            }
        }
    }
}