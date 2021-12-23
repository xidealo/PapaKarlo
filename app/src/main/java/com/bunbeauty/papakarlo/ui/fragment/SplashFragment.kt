package com.bunbeauty.papakarlo.ui.fragment

import androidx.fragment.app.viewModels
import com.bunbeauty.papakarlo.databinding.FragmentSplashBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.SplashViewModel
import com.bunbeauty.papakarlo.ui.base.BaseFragment

class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    override val viewModel: SplashViewModel by viewModels { viewModelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }
}