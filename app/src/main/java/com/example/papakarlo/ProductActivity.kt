package com.example.papakarlo

import androidx.activity.viewModels
import com.example.papakarlo.databinding.ActivityProductBinding
import com.example.papakarlo.di.components.ViewModelComponent
import com.example.papakarlo.ui.activity.base.BaseActivity
import com.example.papakarlo.view_model.ProductViewModel

class ProductActivity : BaseActivity<ActivityProductBinding>() {

    override val dataBindingVariable: Int = BR.viewModel
    override val layoutId: Int = R.layout.activity_product
    override val viewModel: ProductViewModel by viewModels { modelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

}