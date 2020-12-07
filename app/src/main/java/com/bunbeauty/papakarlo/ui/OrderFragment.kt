package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentOrderBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.papakarlo.view_model.OrderViewModel

class OrderFragment : BaseFragment<FragmentOrderBinding, OrderViewModel>() {

    override var viewModelVariable: Int = BR.viewModel
    override var layoutId: Int = R.layout.fragment_order
    override var viewModelClass = OrderViewModel::class.java

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    companion object {
        const val TAG = "OrderFragment"

        @JvmStatic
        fun newInstance() =
            OrderFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}