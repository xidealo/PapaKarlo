package com.bunbeauty.papakarlo.ui.orders

import android.os.Bundle
import android.view.View
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentOrdersBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.adapter.OrdersAdapter
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.papakarlo.ui.base.TopBarFragment
import com.bunbeauty.papakarlo.view_model.OrdersViewModel
import javax.inject.Inject

class OrdersFragment : TopBarFragment<FragmentOrdersBinding, OrdersViewModel>() {

    override var viewModelVariable: Int = BR.viewModel
    override var layoutId: Int = R.layout.fragment_orders
    override var viewModelClass = OrdersViewModel::class.java

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @Inject
    lateinit var ordersAdapter: OrdersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        title = resources.getString(R.string.title_orders)

        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.fragmentOrdersRvResult.adapter = ordersAdapter
        viewModel.orderWithCartProductsLiveData.observe(viewLifecycleOwner) { orderWithCartProducts ->
            ordersAdapter.setItemList(orderWithCartProducts.sortedByDescending { it.order.time })
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            OrdersFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}