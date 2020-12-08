package com.bunbeauty.papakarlo.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.databinding.ActivityMainBinding
import com.bunbeauty.papakarlo.databinding.PartTopBarBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.ConsumerCartFragment
import com.bunbeauty.papakarlo.ui.MenuFragment
import com.bunbeauty.papakarlo.ui.base.BaseActivity
import com.bunbeauty.papakarlo.ui.base.ITopBar
import com.bunbeauty.papakarlo.view_model.MainViewModel
import java.lang.ref.WeakReference

class MainActivity : BaseActivity<ActivityMainBinding>(), MainNavigator, ITopBar {

    override lateinit var topBarBinding: PartTopBarBinding

    override val dataBindingVariable: Int = BR.viewModel
    override val layoutId: Int = R.layout.activity_main
    override val viewModel: MainViewModel by viewModels { modelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        topBarBinding = viewDataBinding.activityVehiclesTbTopBar
        viewModel.mainNavigator = WeakReference(this)

        setCartObserver()
        viewModel.productsLiveData.observe(this) { menuProductList ->
            supportFragmentManager.beginTransaction().replace(
                viewDataBinding.activityProductMenuClFragment.id,
                MenuFragment.newInstance(menuProductList),
                MenuFragment.TAG
            ).commit()
            viewModel.isLoading.set(false)
        }
    }

    private fun setCartObserver() {
        viewModel.cartLiveData.observe(this) { cartText ->
            topBarBinding.partTopBarTvCart.text = cartText
        }
    }

    override fun goToConsumerCart(wishMenuProductList: Set<CartProduct>) {
        if (supportFragmentManager.findFragmentByTag(ConsumerCartFragment.TAG) == null)
            supportFragmentManager.beginTransaction()
                .replace(
                    viewDataBinding.activityProductMenuClFragment.id,
                    ConsumerCartFragment.newInstance(wishMenuProductList),
                    ConsumerCartFragment.TAG
                )
                .addToBackStack(ConsumerCartFragment.TAG)
                .commit()
    }

}