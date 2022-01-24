package com.bunbeauty.papakarlo.ui.fragment.profile.order

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentOrdersBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.presentation.profile.OrdersViewModel
import com.bunbeauty.papakarlo.presentation.state.State
import com.bunbeauty.papakarlo.ui.adapter.OrderAdapter
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.papakarlo.ui.decorator.MarginItemVerticalDecoration
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class OrdersFragment : BaseFragment(R.layout.fragment_orders) {

    @Inject
    lateinit var orderAdapter: OrderAdapter

    @Inject
    lateinit var marginItemVerticalDecoration: MarginItemVerticalDecoration

    override val viewModel: OrdersViewModel by viewModels { viewModelFactory }
    override val viewBinding by viewBinding(FragmentOrdersBinding::bind)

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.run {
            fragmentOrdersRvResult.addItemDecoration(marginItemVerticalDecoration)
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
        viewBinding.fragmentOrdersRvResult.adapter = null

        super.onDestroyView()
    }
}