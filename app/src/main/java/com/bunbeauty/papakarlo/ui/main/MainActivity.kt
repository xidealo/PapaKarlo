package com.bunbeauty.papakarlo.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.model.Product
import com.bunbeauty.papakarlo.databinding.ActivityMainBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.ConsumerCartFragment
import com.bunbeauty.papakarlo.ui.base.BaseActivity
import com.bunbeauty.papakarlo.ui.view.MenuFragment
import com.bunbeauty.papakarlo.view_model.MainViewModel
import java.lang.ref.WeakReference

class MainActivity : BaseActivity<ActivityMainBinding>(), MainNavigator {

    override val dataBindingVariable: Int = BR.viewModel
    override val layoutId: Int = R.layout.activity_main
    override val viewModel: MainViewModel by viewModels { modelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.mainNavigator = WeakReference(this)
        viewModel.productsLiveData.observe(this) {
            supportFragmentManager.beginTransaction()
                .replace(
                    viewDataBinding.activityProductMenuFlMain.id,
                    MenuFragment.newInstance(it),
                    MenuFragment.TAG
                )
                .commit()
        }
        viewModel.getProducts()
    }

    override fun goToConsumerCart(wishProductList: ArrayList<Product>) {
        supportFragmentManager.beginTransaction()
            .replace(
                viewDataBinding.activityProductMenuFlMain.id,
                ConsumerCartFragment.newInstance(wishProductList),
                ConsumerCartFragment.TAG
            )
            .addToBackStack(ConsumerCartFragment.TAG)
            .commit()
    }
}