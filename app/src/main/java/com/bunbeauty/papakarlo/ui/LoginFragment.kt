package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentLoginBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.LoginViewModel
import com.bunbeauty.papakarlo.ui.base.BarsFragment
import com.bunbeauty.papakarlo.ui.view.PhoneTextWatcher

class LoginFragment : BarsFragment<FragmentLoginBinding>() {

    override var layoutId = R.layout.fragment_login
    override val viewModel: LoginViewModel by viewModels { modelFactory }
    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val phoneTextWatcher = PhoneTextWatcher(viewDataBinding.fragmentLoginEtPhone)
        viewDataBinding.fragmentLoginEtPhone.addTextChangedListener(phoneTextWatcher)

        viewDataBinding.fragmentLoginBtnLogin.setOnClickListener {
            viewModel.goToConfirm(viewDataBinding.fragmentLoginEtPhone.text.toString())
        }
        super.onViewCreated(view, savedInstanceState)
    }

}