package com.bunbeauty.papakarlo.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.common.State
import com.bunbeauty.papakarlo.databinding.FragmentSettingsBinding
import com.bunbeauty.papakarlo.delegates.argument
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.gone
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.presentation.profile.SettingsViewModel
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    @Inject
    lateinit var resourcesProvider: IResourcesProvider

    override val viewModel: SettingsViewModel by viewModels { modelFactory }

    private val userUuid: String by argument()

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUser(userUuid)

        viewDataBinding.run {
            viewModel.userState.onEach { state ->
                fragmentSettingsNcPhone.toggleVisibility(state is State.Success)
                fragmentSettingsNcAddEmail.toggleVisibility(state is State.Success)
                fragmentSettingsTcEmail.toggleVisibility(state is State.Success)
                fragmentSettingsPbLoading.toggleVisibility(state !is State.Success)
                when (state) {
                    is State.Success -> {
                        fragmentSettingsNcPhone.cardText = state.data.phone
                        val email = state.data.email
                        fragmentSettingsNcAddEmail.toggleVisibility(email.isNullOrEmpty())
                        fragmentSettingsTcEmail.toggleVisibility(!email.isNullOrEmpty())
                        if (!email.isNullOrEmpty()) {
                            fragmentSettingsTcEmail.cardText = email
                        }
                    }
                    else -> {
                        fragmentSettingsNcPhone.gone()
                        fragmentSettingsNcAddEmail.gone()
                        fragmentSettingsTcEmail.gone()
                    }
                }
            }.startedLaunch()

            fragmentSettingsNcAddEmail.setOnClickListener {
                viewModel.onAddEmailClicked()
            }
            fragmentSettingsNcPhone.setOnClickListener {
                viewModel.onPhoneClicked()
            }
        }
    }
}