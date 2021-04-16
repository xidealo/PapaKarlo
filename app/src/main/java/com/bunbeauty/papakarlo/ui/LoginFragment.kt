package com.bunbeauty.papakarlo.ui

import androidx.fragment.app.viewModels
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentLoginBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.LoginViewModel
import com.bunbeauty.papakarlo.ui.base.BarsFragment


class LoginFragment : BarsFragment<FragmentLoginBinding>() {
    override var layoutId = R.layout.fragment_login
    override val viewModel: LoginViewModel by viewModels { modelFactory }
    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }


}