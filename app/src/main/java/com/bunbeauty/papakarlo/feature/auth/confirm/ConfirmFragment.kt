package com.bunbeauty.papakarlo.feature.auth.confirm

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.delegates.argument
import com.bunbeauty.papakarlo.databinding.FragmentConfirmBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.enums.SuccessLoginDirection
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.extensions.toggleVisibilityInvisibility
import com.bunbeauty.papakarlo.extensions.underlineText
import com.bunbeauty.papakarlo.feature.auth.phone_verification.IPhoneVerificationUtil
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ConfirmFragment : BaseFragment(R.layout.fragment_confirm) {

    @Inject
    lateinit var phoneVerificationUtil: IPhoneVerificationUtil

    override val viewModel: ConfirmViewModel by viewModels { viewModelFactory }
    override val viewBinding by viewBinding(FragmentConfirmBinding::bind)

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

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

            viewModel.resendSecondsInfo.onEach { resendSecondsInfo ->
                fragmentConfirmTvResendSecondsInfo.text = resendSecondsInfo
            }.startedLaunch()
            viewModel.isTimerRun.onEach { isTimerRun ->
                fragmentConfirmTvResendSecondsInfo.toggleVisibility(isTimerRun && !viewModel.isLoading.value)
                fragmentConfirmTvResendCode.toggleVisibility(!isTimerRun && !viewModel.isLoading.value)
            }.startedLaunch()
            viewModel.isLoading.onEach { isLoading ->
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
            }.startedLaunch()

            phoneVerificationUtil.codeSentEvent.onEach { codeSentEvent ->
                verificationId = codeSentEvent.verificationId
                resendToken = codeSentEvent.token
            }.startedLaunch()
            phoneVerificationUtil.authErrorEvent.onEach { authErrorEvent ->
                viewModel.onVerificationError(authErrorEvent.error)
            }.startedLaunch()
            phoneVerificationUtil.authSuccessEvent.onEach {
                viewModel.onSuccessVerified(successLoginDirection)
            }.startedLaunch()
        }
    }
}