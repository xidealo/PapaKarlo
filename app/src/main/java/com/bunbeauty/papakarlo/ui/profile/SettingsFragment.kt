package com.bunbeauty.papakarlo.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.common.State
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentSettingsBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.gone
import com.bunbeauty.papakarlo.extensions.visible
import com.bunbeauty.papakarlo.presentation.profile.SettingsViewModel
import com.bunbeauty.papakarlo.ui.base.BarsFragment
import kotlinx.coroutines.flow.onEach

class SettingsFragment : BarsFragment<FragmentSettingsBinding>() {

    override var layoutId = R.layout.fragment_settings
    override val viewModel: SettingsViewModel by viewModels { modelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setOnClickListeners()

        viewModel.getUser(SettingsFragmentArgs.fromBundle(requireArguments()).userId)
        viewModel.userState.onEach { state ->
            when (state) {
                is State.Success -> {
                    with(viewDataBinding){
                        fragmentSettingsTvPhoneValue.text = state.data.phone
                        if (state.data.email.isEmpty()) {
                            fragmentSettingsTvEmail.text =
                                iResourcesProvider.getString(R.string.title_settings_add_email)
                            fragmentSettingsIvAddEmail.visible()
                            fragmentSettingsIvEditEmail.gone()
                        } else {
                            fragmentSettingsTvEmail.text = state.data.email
                            fragmentSettingsIvAddEmail.gone()
                            fragmentSettingsIvEditEmail.visible()
                        }
                    }
                }
                else -> Unit
            }
        }.startedLaunch(lifecycle)

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setOnClickListeners() {
        viewDataBinding.fragmentSettingsMcvEmail.setOnClickListener {
            viewModel.gotoChangeEmail()
        }
        viewDataBinding.fragmentSettingsMcvPhone.setOnClickListener {
            viewModel.logout()
        }
    }
}