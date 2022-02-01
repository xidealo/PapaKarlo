package com.bunbeauty.papakarlo.feature.splash

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.databinding.FragmentSplashBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent

class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    override val viewModel: SplashViewModel by viewModels { viewModelFactory }
    override val viewBinding by viewBinding(FragmentSplashBinding::bind)

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }
}