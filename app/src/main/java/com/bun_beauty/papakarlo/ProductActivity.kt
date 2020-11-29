package com.bun_beauty.papakarlo

import androidx.activity.viewModels
import com.bun_beauty.papakarlo.databinding.ActivityProductBinding
import com.bun_beauty.papakarlo.di.components.ViewModelComponent
import com.bun_beauty.papakarlo.ui.activity.base.BaseActivity
import com.bun_beauty.papakarlo.view_model.ProductViewModel

class ProductActivity : BaseActivity<ActivityProductBinding>() {

    override val dataBindingVariable: Int = BR.viewModel
    override val layoutId: Int = R.layout.activity_product
    override val viewModel: ProductViewModel by viewModels { modelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

}