package com.bunbeauty.papakarlo.ui.fragment.profile.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.bunbeauty.common.Constants.EMAIL_REQUEST_KEY
import com.bunbeauty.common.Constants.RESULT_EMAIL_KEY
import com.bunbeauty.common.State
import com.bunbeauty.papakarlo.databinding.FragmentSettingsBinding
import com.bunbeauty.papakarlo.delegates.argument
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.presentation.profile.settings.SettingsViewModel
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
            viewModel.profileState.onEach { state ->
                fragmentSettingsNcPhone.toggleVisibility(state is State.Success)
                fragmentSettingsNcAddEmail.toggleVisibility(state is State.Success)
                fragmentSettingsTcEmail.toggleVisibility(state is State.Success)
                fragmentSettingsTcCity.toggleVisibility(state is State.Success)
                fragmentSettingsPbLoading.toggleVisibility(state !is State.Success)
                if (state is State.Success) {
                    fragmentSettingsNcPhone.cardText = state.data.phone
                    val email = state.data.email
                    fragmentSettingsNcAddEmail.toggleVisibility(email.isNullOrEmpty())
                    fragmentSettingsTcEmail.toggleVisibility(!email.isNullOrEmpty())
                    if (!email.isNullOrEmpty()) {
                        fragmentSettingsTcEmail.cardText = email
                    }
                }
            }.startedLaunch()
            viewModel.city.onEach { city ->
                fragmentSettingsTcCity.toggleVisibility(city != null)
                if (city != null) {
                    fragmentSettingsTcCity.cardText = city.name
                }
            }.startedLaunch()

            fragmentSettingsNcAddEmail.setOnClickListener {
                viewModel.onEmailClicked()
            }
            fragmentSettingsTcEmail.setOnClickListener {
                viewModel.onEmailClicked()
            }
            fragmentSettingsNcPhone.setOnClickListener {
                viewModel.onPhoneClicked()
            }
            fragmentSettingsTcCity.setOnClickListener {
                viewModel.onCityClicked()
            }

            setFragmentResultListener(EMAIL_REQUEST_KEY) { _, bundle ->
                bundle.getString(RESULT_EMAIL_KEY)?.let { email ->
                    viewModel.onEmailChanged(email)
                }
            }
        }
    }
}