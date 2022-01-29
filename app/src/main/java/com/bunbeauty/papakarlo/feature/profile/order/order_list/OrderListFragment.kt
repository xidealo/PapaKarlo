package com.bunbeauty.papakarlo.feature.profile.order.order_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.decorator.MarginItemVerticalDecoration
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.databinding.FragmentOrderListBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class OrderListFragment : BaseFragment(R.layout.fragment_order_list) {

    @Inject
    lateinit var orderAdapter: OrderAdapter

    @Inject
    lateinit var marginItemVerticalDecoration: MarginItemVerticalDecoration

    override val viewModel: OrderListViewModel by viewModels { viewModelFactory }
    override val viewBinding by viewBinding(FragmentOrderListBinding::bind)

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.run {
            fragmentOrderListRvResult.addItemDecoration(marginItemVerticalDecoration)
            fragmentOrderListRvResult.adapter = orderAdapter

            viewModel.ordersState.onEach { state ->
                fragmentOrderListPbLoading.toggleVisibility(state is State.Loading)
                fragmentOrderListRvResult.toggleVisibility(state is State.Success)
                fragmentOrderListTvEmptyOrders.toggleVisibility(state is State.Empty)

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
        viewBinding.fragmentOrderListRvResult.adapter = null

        super.onDestroyView()
    }
}