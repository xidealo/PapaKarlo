package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.ActivityProductMenuBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.enums.ProductCode
import com.bunbeauty.papakarlo.ui.adapter.ProductsPagerAdapter
import com.bunbeauty.papakarlo.ui.base.BaseActivity
import com.bunbeauty.papakarlo.view_model.ProductMenuViewModel
import com.google.android.material.tabs.TabLayoutMediator

class ProductMenuActivity : BaseActivity<ActivityProductMenuBinding>() {

    override val dataBindingVariable: Int = BR.viewModel
    override val layoutId: Int = R.layout.activity_product_menu
    override val viewModel: ProductMenuViewModel by viewModels { modelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLiveDataProducts()
        viewModel.getProducts()
    }

    private fun setLiveDataProducts(){
        viewModel.productsLiveData.observe(this){
            viewDataBinding.activityMainVp.adapter = ProductsPagerAdapter(
                listOf(
                    ProductsFragment.newInstance(ProductCode.All, it),
                    ProductsFragment.newInstance(ProductCode.Pizza, it),
                    ProductsFragment.newInstance(ProductCode.Hamburger, it),
                    ProductsFragment.newInstance(ProductCode.Potato, it),
                    ProductsFragment.newInstance(ProductCode.OnCoals, it),
                ),
                this
            )

            val tabNameList = arrayListOf(
                ProductCode.All.name,
                ProductCode.Pizza.name,
                ProductCode.Hamburger.name,
                ProductCode.Potato.name,
                ProductCode.OnCoals.name
            )

            TabLayoutMediator(
                viewDataBinding.activityMainTl,
                viewDataBinding.activityMainVp
            ) { tab, i ->
                tab.text = tabNameList[i]
            }.attach()
        }
    }
}