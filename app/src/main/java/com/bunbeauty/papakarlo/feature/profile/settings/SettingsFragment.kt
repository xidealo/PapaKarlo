package com.bunbeauty.papakarlo.feature.profile.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.common.Constants.EMAIL_REQUEST_KEY
import com.bunbeauty.common.Constants.RESULT_EMAIL_KEY
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.databinding.FragmentSettingsBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.toggleVisibility

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    override val viewModel: SettingsViewModel by viewModels { viewModelFactory }
    override val viewBinding by viewBinding(FragmentSettingsBinding::bind)

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.run {
            viewModel.userState.startedLaunch { state ->
                fragmentSettingsGroupMain.toggleVisibility(state is State.Success)
                fragmentSettingsPbLoading.toggleVisibility(state !is State.Success)
                if (state is State.Success) {
                    fragmentSettingsTcPhone.cardText = state.data.phone
                    val email = state.data.email
                    fragmentSettingsNcAddEmail.toggleVisibility(email.isNullOrEmpty())
                    fragmentSettingsTcEmail.toggleVisibility(!email.isNullOrEmpty())
                    fragmentSettingsTcEmail.cardText = email ?: ""
                }
            }
            viewModel.cityName.startedLaunch { cityName ->
                fragmentSettingsTcCity.cardText = cityName
            }

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