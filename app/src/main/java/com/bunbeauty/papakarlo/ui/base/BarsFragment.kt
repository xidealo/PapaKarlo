package com.bunbeauty.papakarlo.ui.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import com.bunbeauty.papakarlo.view_model.base.ToolbarViewModel

abstract class BarsFragment<T : ViewDataBinding, VM : ToolbarViewModel> : BaseFragment<T, VM>() {

    open val isToolbarVisible = true
    open val isToolbarLogoVisible = false
    open val isBottomBarVisible = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? IBottomNavigationBar)?.setupBottomNavigationBar(isBottomBarVisible)
        (activity as? IToolbar)?.setupToolbar(isToolbarVisible, isToolbarLogoVisible)
        subscribe(viewModel.cartLiveData) { cartText ->
            (activity as? IToolbar)?.setCartText(cartText)
        }
    }
}