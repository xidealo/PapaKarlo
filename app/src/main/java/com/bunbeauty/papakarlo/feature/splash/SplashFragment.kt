package com.bunbeauty.papakarlo.feature.splash

import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.databinding.FragmentSplashBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    override val viewModel: SplashViewModel by viewModel()
    override val viewBinding by viewBinding(FragmentSplashBinding::bind)
}
