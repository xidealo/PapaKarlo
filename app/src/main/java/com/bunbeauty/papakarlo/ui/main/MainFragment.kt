package com.bunbeauty.papakarlo.ui.main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentMainBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.base.CartClickableFragment
import com.bunbeauty.papakarlo.ui.main.MainFragmentDirections.actionMainFragmentToCartFragment
import com.bunbeauty.papakarlo.view_model.MainViewModel

class MainFragment: CartClickableFragment<FragmentMainBinding, MainViewModel>(), MainNavigator {

    override var layoutId = R.layout.fragment_main
    override var viewModelClass: Class<MainViewModel> = MainViewModel::class.java
    override var viewModelVariable: Int = BR.viewModel

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NavigationUI.setupWithNavController(
            viewDataBinding.fragmentMainBnvNavigationBar,
            (childFragmentManager.findFragmentById(R.id.fragment_main_fcv_container) as NavHostFragment).findNavController()
        )
    }

    override fun goToCart(view: View) {
        findNavController().navigate(actionMainFragmentToCartFragment())
    }
}