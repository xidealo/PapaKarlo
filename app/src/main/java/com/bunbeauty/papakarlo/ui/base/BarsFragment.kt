package com.bunbeauty.papakarlo.ui.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bunbeauty.papakarlo.presentation.base.ToolbarViewModel
import kotlinx.coroutines.flow.onEach

abstract class BarsFragment<T : ViewDataBinding> : BaseFragment<T>() {

    override val viewModel: ToolbarViewModel by viewModels { modelFactory }

    open val isToolbarVisible = true
    open val isToolbarLogoVisible = false
    open val isToolbarCartProductVisible = true
    open val isBottomBarVisible = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? IBottomNavigationBar)?.setupBottomNavigationBar(isBottomBarVisible)
        (activity as? IToolbar)?.setToolbarConfiguration(
            isToolbarVisible,
            isToolbarLogoVisible,
            isToolbarCartProductVisible
        )
        viewModel.cartFlow.onEach { cartText ->
            (activity as? IToolbar)?.setCartText(cartText)
        }.startedLaunch(lifecycle)
    }
}