package com.bunbeauty.papakarlo.ui.fragment.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
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

class LoginFragment : BaseFragment(R.layout.fragment_login) {

    @Inject
    lateinit var phoneVerificationUtil: IPhoneVerificationUtil

    override val viewModel: LoginViewModel by viewModels { viewModelFactory }
    override val viewBinding by viewBinding(FragmentLoginBinding::bind)

    private val successLoginDirection: SuccessLoginDirection by argument()

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onViewCreated()
        viewBinding.run {
            val phoneTextWatcher = PhoneTextWatcher(fragmentLoginEtPhone)
            fragmentLoginEtPhone.addTextChangedListener(phoneTextWatcher)

            fragmentLoginBtnLogin.setOnClickListener {
                hideKeyboard()
                viewModel.onNextClicked(fragmentLoginEtPhone.text.toString())
            }

            fragmentLoginEtPhone.focusAndShowKeyboard()
        }

        viewModel.phoneCheckedEvent.onEach { phoneCheckedEvent ->
            phoneVerificationUtil.sendVerificationCode(
                phone = phoneCheckedEvent.phone,
                activity = requireActivity()
            )
        }.startedLaunch()
        viewModel.isLoading.onEach { isLoading ->
            viewBinding.fragmentLoginGroupMain.toggleVisibility(!isLoading)
            viewBinding.fragmentLoginPbLoading.toggleVisibility(isLoading)
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
            viewModel.onSuccessVerified(successLoginDirection)
        }.startedLaunch()
    }

}