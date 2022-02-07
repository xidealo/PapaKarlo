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
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderListFragment : BaseFragment(R.layout.fragment_order_list) {


    val orderAdapter: OrderAdapter by inject()
    val marginItemVerticalDecoration: MarginItemVerticalDecoration by inject()

    override val viewModel: OrderListViewModel by viewModel()
    override val viewBinding by viewBinding(FragmentOrderListBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.run {
            fragmentOrderListRvResult.addItemDecoration(marginItemVerticalDecoration)
            fragmentOrderListRvResult.adapter = orderAdapter

            viewModel.ordersState.startedLaunch { state ->
                fragmentOrderListPbLoading.toggleVisibility(state is State.Loading)
                fragmentOrderListRvResult.toggleVisibility(state is State.Success)
                fragmentOrderListTvEmptyOrders.toggleVisibility(state is State.Empty)

                if (state is State.Success) {
                    orderAdapter.submitList(state.data)
                }
            }
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