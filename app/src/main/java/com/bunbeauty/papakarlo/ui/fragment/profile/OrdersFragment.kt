package com.bunbeauty.papakarlo.ui.fragment.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.common.State
import com.bunbeauty.papakarlo.databinding.FragmentOrdersBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.gone
import com.bunbeauty.papakarlo.extensions.startedLaunch
import com.bunbeauty.papakarlo.extensions.visible
import com.bunbeauty.papakarlo.presentation.profile.OrdersViewModel
import com.bunbeauty.papakarlo.ui.adapter.OrdersAdapter
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class OrdersFragment : BaseFragment<FragmentOrdersBinding>() {

    override val viewModel: OrdersViewModel by viewModels { modelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @Inject
    lateinit var ordersAdapter: OrdersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.fragmentOrdersRvResult.adapter = ordersAdapter
        viewModel.ordersState.onEach { state ->
            when (state) {
                is State.Success -> {
                    ordersAdapter.submitList(state.data)
                    with(viewDataBinding) {
                        fragmentOrdersPbLoading.gone()
                        fragmentOrdersRvResult.visible()
                        fragmentOrdersTvEmptyOrders.gone()
                    }
                }
                is State.Loading -> {
                    with(viewDataBinding) {
                        fragmentOrdersPbLoading.visible()
                        fragmentOrdersRvResult.gone()
                        fragmentOrdersTvEmptyOrders.gone()
                    }
                }
                is State.Empty -> {
                    with(viewDataBinding) {
                        fragmentOrdersPbLoading.gone()
                        fragmentOrdersRvResult.gone()
                        fragmentOrdersTvEmptyOrders.visible()
                    }
                }
                else -> Unit
            }
        }.startedLaunch(viewLifecycleOwner)

        ordersAdapter.onItemClickListener = { order ->
            viewModel.onOrderClicked(order)
        }
    }
    override fun onDestroyView() {
        viewDataBinding.fragmentOrdersRvResult.adapter = null

        super.onDestroyView()
    }
}