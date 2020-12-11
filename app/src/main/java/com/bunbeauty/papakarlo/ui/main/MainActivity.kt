package com.bunbeauty.papakarlo.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.databinding.ActivityMainBinding
import com.bunbeauty.papakarlo.databinding.PartBottomPanelBinding
import com.bunbeauty.papakarlo.databinding.PartTopBarBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.gone
import com.bunbeauty.papakarlo.extensions.visible
import com.bunbeauty.papakarlo.ui.consumer_cart.ConsumerCartFragment
import com.bunbeauty.papakarlo.ui.MenuFragment
import com.bunbeauty.papakarlo.ui.base.BaseActivity
import com.bunbeauty.papakarlo.ui.base.IBottomBar
import com.bunbeauty.papakarlo.ui.base.ITopBar
import com.bunbeauty.papakarlo.ui.contacts.ContactsFragment
import com.bunbeauty.papakarlo.ui.orders.OrdersFragment
import com.bunbeauty.papakarlo.view_model.MainViewModel
import java.lang.ref.WeakReference

class MainActivity : BaseActivity<ActivityMainBinding>(), MainNavigator, ITopBar, IBottomBar {

    override lateinit var topBarBinding: PartTopBarBinding
    override lateinit var bottomBarBinding: PartBottomPanelBinding

    override val dataBindingVariable: Int = BR.viewModel
    override val layoutId: Int = R.layout.activity_main
    override val viewModel: MainViewModel by viewModels { modelFactory }
    var menuProducts: ArrayList<MenuProduct> = arrayListOf()
    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        topBarBinding = viewDataBinding.activityMainTbTopBar
        bottomBarBinding = viewDataBinding.activityMainTbBottomBar
        viewModel.mainNavigator = WeakReference(this)
        initBottomPanel(2)
        setCartObserver()
        viewModel.productsLiveData.observe(this) { menuProductList ->
            menuProducts.addAll(menuProductList)
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
                    ConsumerCartFragment.newInstance(),
                    ConsumerCartFragment.TAG
                )
                .addToBackStack(ConsumerCartFragment.TAG)
                .commit()
    }

    override fun goToContacts() {
        supportFragmentManager.beginTransaction()
            .replace(
                viewDataBinding.activityProductMenuClFragment.id,
                ContactsFragment.newInstance(),
                ContactsFragment.TAG
            )
            .addToBackStack(ContactsFragment.TAG)
            .commit()
    }

    override fun goToMenu() {
        supportFragmentManager.beginTransaction().replace(
            viewDataBinding.activityProductMenuClFragment.id,
            MenuFragment.newInstance(menuProducts),
            MenuFragment.TAG
        ).addToBackStack(MenuFragment.TAG).commit()
    }

    override fun goToOrders() {
        supportFragmentManager.beginTransaction().replace(
            viewDataBinding.activityProductMenuClFragment.id,
            OrdersFragment.newInstance(),
            OrdersFragment.TAG
        ).addToBackStack(OrdersFragment.TAG).commit()
    }

    fun hideBottomPanel() {
        viewDataBinding.activityMainTbBottomBar.partBottomBarTb.gone()
    }

    fun showBottomPanel() {
        viewDataBinding.activityMainTbBottomBar.partBottomBarTb.visible()
    }

}