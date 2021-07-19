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

    override val isCartVisible = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val phoneTextWatcher = PhoneTextWatcher(viewDataBinding.fragmentLoginEtPhone)
        viewDataBinding.fragmentLoginEtPhone.addTextChangedListener(phoneTextWatcher)
        textInputMap[Constants.PHONE_ERROR_KEY] = viewDataBinding.fragmentLoginTilPhone
        viewDataBinding.fragmentLoginBtnLogin.setOnClickListener {
            viewModel.goToConfirm(
                viewDataBinding.fragmentLoginEtPhone.text.toString(),
                ""
            )
        }
        super.onViewCreated(view, savedInstanceState)
    }

}