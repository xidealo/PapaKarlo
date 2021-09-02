package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.common.Constants
import com.bunbeauty.papakarlo.databinding.FragmentLoginBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.login.LoginViewModel
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.papakarlo.ui.view.PhoneTextWatcher

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override val viewModel: LoginViewModel by viewModels { modelFactory }
    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.run {
            val phoneTextWatcher = PhoneTextWatcher(fragmentLoginEtPhone)
            fragmentLoginEtPhone.addTextChangedListener(phoneTextWatcher)
            fragmentLoginBtnLogin.setOnClickListener {
                viewModel.goToConfirm(fragmentLoginEtPhone.text.toString())
            }
        }
    }

}