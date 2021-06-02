package com.bunbeauty.papakarlo.ui.profile

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentSettingsBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.profile.SettingsViewModel
import com.bunbeauty.papakarlo.ui.base.BarsFragment

class SettingsFragment : BarsFragment<FragmentSettingsBinding>() {

    override var layoutId = R.layout.fragment_settings
    override val viewModel: SettingsViewModel by viewModels { modelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setOnClickListeners()
        viewDataBinding.fragmentSettingsTvPhoneValue.text =
            SettingsFragmentArgs.fromBundle(requireArguments()).user.phone
        viewDataBinding.fragmentSettingsEtEmail.setText(
            SettingsFragmentArgs.fromBundle(
                requireArguments()
            ).user.email
        )
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setOnClickListeners() {
        viewDataBinding.fragmentSettingsBtnSaveAddress.setOnClickListener {
            val user = SettingsFragmentArgs.fromBundle(requireArguments()).user
            if (viewDataBinding.fragmentSettingsEtEmail.text.toString() != user.email) {
                user.email = viewDataBinding.fragmentSettingsEtEmail.text.toString()
                viewModel.updateUser(user)
            }
            val inputMethodManager =
                requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        }
        viewDataBinding.fragmentSettingsBtnLogout.setOnClickListener {
            viewModel.logout()
        }
    }
}