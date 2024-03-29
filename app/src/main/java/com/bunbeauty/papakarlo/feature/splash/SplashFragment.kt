package com.bunbeauty.papakarlo.feature.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.extension.navigateSafe
import com.bunbeauty.papakarlo.databinding.FragmentSplashBinding
import com.bunbeauty.papakarlo.feature.splash.SplashFragmentDirections.toMenuFragment
import com.bunbeauty.papakarlo.feature.splash.SplashFragmentDirections.toSelectCityFragment
import com.bunbeauty.papakarlo.feature.splash.SplashFragmentDirections.toUpdateFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    override val viewModel: SplashViewModel by viewModel()
    override val viewBinding by viewBinding(FragmentSplashBinding::bind)

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.checkAppVersion()
            }
        }
        viewModel.eventList.startedLaunch { eventList ->
            eventList.forEach { event ->
                when (event) {
                    SplashEvent.NavigateToUpdateEvent -> {
                        findNavController().navigateSafe(toUpdateFragment())
                    }
                    SplashEvent.NavigateToSelectCityEvent -> {
                        findNavController().navigateSafe(toSelectCityFragment())
                    }
                    SplashEvent.NavigateToMenuEvent -> {
                        findNavController().navigateSafe(toMenuFragment())
                    }
                }
                viewModel.consumeEventList(eventList)
            }
        }
    }
}
