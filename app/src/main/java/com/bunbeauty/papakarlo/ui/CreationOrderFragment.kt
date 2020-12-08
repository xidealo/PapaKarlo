package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentCreationOrderBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.papakarlo.ui.main.MainActivity
import com.bunbeauty.papakarlo.view_model.OrderViewModel

class CreationOrderFragment : BaseFragment<FragmentCreationOrderBinding, OrderViewModel>() {

    override var viewModelVariable: Int = BR.viewModel
    override var layoutId: Int = R.layout.fragment_creation_order
    override var viewModelClass = OrderViewModel::class.java

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle("Оформление заказа")
    }

    companion object {
        const val TAG = "OrderFragment"

        @JvmStatic
        fun newInstance() =
            CreationOrderFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}