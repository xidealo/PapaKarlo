package com.bunbeauty.papakarlo.ui.fragment.profile.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.bunbeauty.common.Constants.EMAIL_REQUEST_KEY
import com.bunbeauty.common.Constants.RESULT_EMAIL_KEY
import com.bunbeauty.common.State
import com.bunbeauty.papakarlo.databinding.FragmentSettingsBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.presentation.profile.settings.SettingsViewModel
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import kotlinx.coroutines.flow.onEach

class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    override val viewModel: SettingsViewModel by viewModels { viewModelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.run {
            viewModel.userState.onEach { state ->
                fragmentSettingsGroupMain.toggleVisibility(state is State.Success)
                fragmentSettingsPbLoading.toggleVisibility(state !is State.Success)
                if (state is State.Success) {
                    fragmentSettingsTcPhone.cardText = state.data.phone
                    val email = state.data.email
                    fragmentSettingsNcAddEmail.toggleVisibility(email.isEmpty())
                    fragmentSettingsTcEmail.toggleVisibility(email.isNotEmpty())
                    fragmentSettingsTcEmail.cardText = email
                }
            }.startedLaunch()
            viewModel.cityName.onEach { cityName ->
                fragmentSettingsTcCity.cardText = cityName
            }.startedLaunch()

            fragmentSettingsNcAddEmail.setOnClickListener {
                viewModel.onEmailClicked()
            }
            fragmentSettingsTcEmail.setOnClickListener {
                viewModel.onEmailClicked()
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