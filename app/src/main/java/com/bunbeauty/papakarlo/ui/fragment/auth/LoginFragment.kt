package com.bunbeauty.papakarlo.ui.fragment.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.papakarlo.databinding.FragmentLoginBinding
import com.bunbeauty.papakarlo.delegates.argument
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.focusAndShowKeyboard
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.phone_verification.IPhoneVerificationUtil
import com.bunbeauty.papakarlo.presentation.login.LoginViewModel
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.papakarlo.ui.custom.PhoneTextWatcher
import com.bunbeauty.presentation.enums.SuccessLoginDirection
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    @Inject
    lateinit var phoneVerificationUtil: IPhoneVerificationUtil

    override val viewModel: LoginViewModel by viewModels { viewModelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    private val successLoginDirection: SuccessLoginDirection by argument()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onViewCreated()
        viewDataBinding.run {
            val phoneTextWatcher = PhoneTextWatcher(fragmentLoginEtPhone)
            fragmentLoginEtPhone.addTextChangedListener(phoneTextWatcher)
            fragmentLoginEtPhone.focusAndShowKeyboard()
            fragmentLoginBtnLogin.setOnClickListener {
                hideKeyboard()
                viewModel.onNextClicked(fragmentLoginEtPhone.text.toString())
            }
        }

        viewModel.phoneCheckedEvent.onEach { phoneCheckedEvent ->
            phoneVerificationUtil.sendVerificationCode(
                phone = phoneCheckedEvent.phone,
                activity = requireActivity()
            )
        }.startedLaunch()
        viewModel.isLoading.onEach { isLoading ->
            viewDataBinding.fragmentLoginGroupMain.toggleVisibility(!isLoading)
            viewDataBinding.fragmentLoginPbLoading.toggleVisibility(isLoading)
        }.startedLaunch()
        phoneVerificationUtil.codeSentEvent.onEach { codeSentEvent ->
            viewModel.onCodeSent(
                codeSentEvent.phone,
                codeSentEvent.verificationId,
                codeSentEvent.token,
                successLoginDirection
            )
        }.startedLaunch()
        phoneVerificationUtil.authErrorEvent.onEach { authErrorEvent ->
            viewModel.onVerificationError(authErrorEvent.error)
        }.startedLaunch()
        phoneVerificationUtil.authSuccessEvent.onEach {
            viewModel.onSuccessVerified()
        }.startedLaunch()
    }

}