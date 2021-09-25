package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.papakarlo.databinding.FragmentConfirmBinding
import com.bunbeauty.papakarlo.delegates.argument
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.extensions.toggleVisibilityInvisibility
import com.bunbeauty.papakarlo.extensions.underlineText
import com.bunbeauty.papakarlo.phone_verification.IPhoneVerificationUtil
import com.bunbeauty.papakarlo.presentation.login.ConfirmViewModel
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ConfirmFragment : BaseFragment<FragmentConfirmBinding>() {

    @Inject
    lateinit var resourcesProvider: IResourcesProvider

    @Inject
    lateinit var phoneVerificationUtil: IPhoneVerificationUtil

    override val viewModel: ConfirmViewModel by viewModels { modelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    private val phone: String by argument()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.startResendTimer()
        phoneVerificationUtil.sendVerificationCode(viewModel.formatPhone(phone), requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.run {
            fragmentConfirmTvPhoneInformation.text = viewModel.getPhoneInfo(phone)
            fragmentConfirmEtCode.setOnPinEnteredListener { code ->
                viewModel.onCodeEntered(phoneVerificationUtil.verifyCode(code.toString()))
            }
            fragmentConfirmTvResendCode.underlineText()
            fragmentConfirmTvResendCode.setOnClickListener {
                viewModel.startResendTimer()
                phoneVerificationUtil.resendVerificationCode(
                    viewModel.formatPhone(phone),
                    requireActivity()
                )
            }
            fragmentConfirmTvChangePhone.underlineText()
            fragmentConfirmTvChangePhone.setOnClickListener {
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
                fragmentConfirmEtCode.setText("")
                viewDataBinding.fragmentConfirmPbLoading.toggleVisibility(isLoading)
                viewDataBinding.fragmentConfirmEtCode.toggleVisibilityInvisibility(!isLoading)
                fragmentConfirmTvResendSecondsInfo.toggleVisibility(viewModel.isTimerRun.value && !isLoading)
                fragmentConfirmTvResendCode.toggleVisibility(!viewModel.isTimerRun.value && !isLoading)
            }.startedLaunch()
        }
    }
}