package com.bunbeauty.papakarlo.feature.auth.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.delegates.argument
import com.bunbeauty.papakarlo.databinding.FragmentLoginBinding
import com.bunbeauty.papakarlo.enums.SuccessLoginDirection
import com.bunbeauty.papakarlo.extensions.focusAndShowKeyboard
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.feature.auth.phone_verification.IPhoneVerificationUtil
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.java.KoinJavaComponent.inject

class LoginFragment : BaseFragment(R.layout.fragment_login) {

    val phoneVerificationUtil: IPhoneVerificationUtil by inject()

    override val viewModel: LoginViewModel by viewModel()
    override val viewBinding by viewBinding(FragmentLoginBinding::bind)

    private val successLoginDirection: SuccessLoginDirection by argument()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.run {
            val phoneTextWatcher = PhoneTextWatcher(fragmentLoginEtPhone)
            fragmentLoginEtPhone.addTextChangedListener(phoneTextWatcher)

            fragmentLoginBtnLogin.setOnClickListener {
                hideKeyboard()
                viewModel.onNextClicked(fragmentLoginEtPhone.text.toString())
            }

            fragmentLoginEtPhone.focusAndShowKeyboard()
        }

        viewModel.phoneCheckedEvent.startedLaunch { phoneCheckedEvent ->
            phoneVerificationUtil.sendVerificationCode(
                phone = phoneCheckedEvent.phone,
                activity = requireActivity()
            )
        }
        viewModel.isLoading.startedLaunch { isLoading ->
            viewBinding.fragmentLoginGroupMain.toggleVisibility(!isLoading)
            viewBinding.fragmentLoginPbLoading.toggleVisibility(isLoading)
        }
        phoneVerificationUtil.codeSentEvent.startedLaunch { codeSentEvent ->
            viewModel.onCodeSent(
                codeSentEvent.phone,
                codeSentEvent.verificationId,
                codeSentEvent.token,
                successLoginDirection
            )
        }
        phoneVerificationUtil.authErrorEvent.startedLaunch { authErrorEvent ->
            viewModel.onVerificationError(authErrorEvent.error)
        }
        phoneVerificationUtil.authSuccessEvent.startedLaunch {
            viewModel.onSuccessVerified(successLoginDirection)
        }
    }

    override fun onStop() {
        viewModel.stopLoading()
        super.onStop()
    }

}