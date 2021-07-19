package com.bunbeauty.papakarlo.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.common.State
import com.bunbeauty.domain.util.resources.IResourcesProvider
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentSettingsBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.gone
import com.bunbeauty.papakarlo.extensions.startedLaunch
import com.bunbeauty.papakarlo.extensions.visible
import com.bunbeauty.papakarlo.presentation.profile.SettingsViewModel
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    @Inject
    lateinit var resourcesProvider: IResourcesProvider

    override val viewModel: SettingsViewModel by viewModels { modelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()

        viewModel.getUser(SettingsFragmentArgs.fromBundle(requireArguments()).userId)
        viewModel.userEntityState.onEach { state ->
            when (state) {
                is State.Success -> {
                    with(viewDataBinding){
                        fragmentSettingsTvPhoneValue.text = state.data.phone
                        if (state.data.email.isEmpty()) {
                            fragmentSettingsTvEmail.text =
                                resourcesProvider.getString(R.string.title_settings_add_email)
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
        }.startedLaunch(viewLifecycleOwner)
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