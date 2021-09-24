package com.bunbeauty.papakarlo.ui.fragment.profile.order

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.common.State
import com.bunbeauty.papakarlo.databinding.FragmentOrdersBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.presentation.profile.OrdersViewModel
import com.bunbeauty.papakarlo.ui.adapter.OrderAdapter
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.papakarlo.ui.custom.MarginItemDecoration
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class OrdersFragment : BaseFragment<FragmentOrdersBinding>() {

    @Inject
    lateinit var orderAdapter: OrderAdapter

    @Inject
    lateinit var mardinItemDecoration: MarginItemDecoration

    override val viewModel: OrdersViewModel by viewModels { modelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.run {
            fragmentOrdersRvResult.addItemDecoration(mardinItemDecoration)
            fragmentOrdersRvResult.adapter = orderAdapter

            viewModel.ordersState.onEach { state ->
                fragmentOrdersPbLoading.toggleVisibility(state is State.Loading)
                fragmentOrdersRvResult.toggleVisibility(state is State.Success)
                fragmentOrdersTvEmptyOrders.toggleVisibility(state is State.Empty)

                if (state is State.Success) {
                    orderAdapter.submitList(state.data)
                }
            }.startedLaunch()
        }

        orderAdapter.setOnItemClickListener { order ->
            viewModel.onOrderClicked(order)
        }
    }
    override fun onDestroyView() {
        viewDataBinding.fragmentOrdersRvResult.adapter = null

        super.onDestroyView()
    }
}