package com.bun_beauty.papakarlo.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.bun_beauty.papakarlo.BR
import com.bun_beauty.papakarlo.R
import com.bun_beauty.papakarlo.databinding.ActivityConsumerCartBinding
import com.bun_beauty.papakarlo.di.components.ViewModelComponent
import com.bun_beauty.papakarlo.ui.activity.base.BaseActivity
import com.bun_beauty.papakarlo.view_model.ConsumerCartViewModel

class ConsumerCartActivity : BaseActivity<ActivityConsumerCartBinding>() {

    override val dataBindingVariable: Int = BR.viewModel
    override val layoutId: Int = R.layout.activity_consumer_cart
    override val viewModel: ConsumerCartViewModel by viewModels { modelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consumer_cart)
    }
}