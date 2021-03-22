package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import android.view.View
import com.bunbeauty.papakarlo.databinding.FragmentOrdersBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.adapter.OrdersAdapter
import com.bunbeauty.papakarlo.ui.base.BarsFragment
import com.bunbeauty.papakarlo.presentation.OrdersViewModel
import javax.inject.Inject

class OrdersFragment : BarsFragment<FragmentOrdersBinding, OrdersViewModel>() {

    @Inject
    lateinit var ordersAdapter: OrdersAdapter

    override val isBottomBarVisible = true

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.fragmentOrdersRvResult.adapter = ordersAdapter
        viewModel.orderWithCartProductsLiveData.observe(viewLifecycleOwner) { orderWithCartProducts ->
            ordersAdapter.setItemList(orderWithCartProducts.sortedByDescending { it.orderEntity.time })
        }
    }
}