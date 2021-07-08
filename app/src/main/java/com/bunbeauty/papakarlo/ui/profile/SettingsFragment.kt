package com.bunbeauty.papakarlo.ui.profile

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentSettingsBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.gone
import com.bunbeauty.papakarlo.extensions.visible
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

        if (SettingsFragmentArgs.fromBundle(
                requireArguments()
            ).user.email.isEmpty()
        ) {
            viewDataBinding.fragmentSettingsTvEmail.text =
                iResourcesProvider.getString(R.string.title_settings_add_email)
            viewDataBinding.fragmentSettingsIvAddEmail.visible()
            viewDataBinding.fragmentSettingsIvEditEmail.gone()
        } else {
            viewDataBinding.fragmentSettingsTvEmail.text = SettingsFragmentArgs.fromBundle(
                requireArguments()
            ).user.email
            viewDataBinding.fragmentSettingsIvAddEmail.gone()
            viewDataBinding.fragmentSettingsIvEditEmail.visible()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setOnClickListeners() {
        /*       viewDataBinding.fragmentSettingsBtnSaveAddress.setOnClickListener {
                   val user = SettingsFragmentArgs.fromBundle(requireArguments()).user
                   if (viewDataBinding.fragmentSettingsEtEmail.text.toString() != user.email) {
                       user.email = viewDataBinding.fragmentSettingsEtEmail.text.toString()
                       viewModel.updateUser(user)
                   }
                   val inputMethodManager =
                       requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                   inputMethodManager.hideSoftInputFromWindow(
                       requireActivity().currentFocus?.windowToken,
                       0
                   )
               }*/
        viewDataBinding.fragmentSettingsMcvPhone.setOnClickListener {
            viewModel.logout()
        }
    }
}