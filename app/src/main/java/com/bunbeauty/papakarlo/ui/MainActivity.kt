package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.ActivityMainBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.enums.ProductCode
import com.bunbeauty.papakarlo.ui.adapter.ProductsPagerAdapter
import com.bunbeauty.papakarlo.ui.base.BaseActivity
import com.bunbeauty.papakarlo.ui.view.MenuFragment
import com.bunbeauty.papakarlo.view_model.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val dataBindingVariable: Int = BR.viewModel
    override val layoutId: Int = R.layout.activity_main
    override val viewModel: MainViewModel by viewModels { modelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.productsLiveData.observe(this) {
            supportFragmentManager.beginTransaction()
                .replace(
                    viewDataBinding.activityProductMenuFlMain.id,
                    MenuFragment.newInstance(it)
                )
                .commit()
        }
        viewModel.getProducts()
    }
}