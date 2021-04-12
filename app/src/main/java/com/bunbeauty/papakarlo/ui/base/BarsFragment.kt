package com.bunbeauty.papakarlo.ui.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bunbeauty.papakarlo.presentation.MainViewModel
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.presentation.base.ToolbarViewModel
import javax.inject.Inject

abstract class BarsFragment<T : ViewDataBinding> : BaseFragment<T>() {

    override val viewModel: ToolbarViewModel by viewModels { modelFactory }

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