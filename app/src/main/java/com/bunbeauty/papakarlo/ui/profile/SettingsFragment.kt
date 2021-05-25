package com.bunbeauty.papakarlo.ui.profile

import androidx.fragment.app.viewModels
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentSettingsBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.OrdersViewModel
import com.bunbeauty.papakarlo.presentation.SettingsViewModel
import com.bunbeauty.papakarlo.ui.base.BarsFragment

class SettingsFragment : BarsFragment<FragmentSettingsBinding>() {

    override var layoutId = R.layout.fragment_settings
    override val viewModel: SettingsViewModel by viewModels { modelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }


}