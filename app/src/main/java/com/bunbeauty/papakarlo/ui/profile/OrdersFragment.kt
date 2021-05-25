package com.bunbeauty.papakarlo.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentOrdersBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.adapter.OrdersAdapter
import com.bunbeauty.papakarlo.ui.base.BarsFragment
import com.bunbeauty.papakarlo.presentation.profile.OrdersViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class OrdersFragment : BarsFragment<FragmentOrdersBinding>() {

    override var layoutId = R.layout.fragment_orders
    override val viewModel: OrdersViewModel by viewModels { modelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @Inject
    lateinit var ordersAdapter: OrdersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ordersAdapter.ordersViewModel = viewModel
        viewDataBinding.fragmentOrdersRvResult.adapter = ordersAdapter
        viewModel.orderWithCartProductsLiveData.onEach { orderWithCartProducts ->
            ordersAdapter.setItemList(orderWithCartProducts.sortedByDescending { it.orderEntity.time }
                .also { ordersList -> ordersList.map { it.uuid = it.orderEntity.uuid } })
        }.launchWhenStarted(lifecycleScope)
    }
}