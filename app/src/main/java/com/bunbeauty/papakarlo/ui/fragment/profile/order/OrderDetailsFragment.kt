package com.bunbeauty.papakarlo.ui.fragment.profile.order

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.papakarlo.databinding.FragmentOrderDetailsBinding
import com.bunbeauty.papakarlo.delegates.argument
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.gone
import com.bunbeauty.papakarlo.extensions.strikeOutText
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.presentation.profile.OrderDetailsViewModel
import com.bunbeauty.papakarlo.presentation.state.State
import com.bunbeauty.papakarlo.ui.adapter.OrderProductAdapter
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.papakarlo.ui.custom.MarginItemDecoration
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class OrderDetailsFragment : BaseFragment<FragmentOrderDetailsBinding>() {

    @Inject
    lateinit var orderProductAdapter: OrderProductAdapter

    @Inject
    lateinit var marginItemDecoration: MarginItemDecoration

    override val viewModel: OrderDetailsViewModel by viewModels { viewModelFactory }

    private val orderUuid: String by argument()

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getOrder(orderUuid)
        viewModel.orderState.onEach { state ->
            viewDataBinding.fragmentOrderDetailsMcvOrderDetails.toggleVisibility(state is State.Success)
            viewDataBinding.fragmentOrderDetailsRvProductList.toggleVisibility(state is State.Success)
            viewDataBinding.fragmentOrderDetailsVBlur.toggleVisibility(state is State.Success)
            viewDataBinding.fragmentOrderDetailsClBottomCost.toggleVisibility(state is State.Success)
            viewDataBinding.fragmentOrderDetailsPbLoading.toggleVisibility(state !is State.Success)

            if (state is State.Success) {
                val order = state.data

                viewDataBinding.run {
                    fragmentOrderDetailsPsvStatus.currentStep = order.stepCount
                    fragmentOrderDetailsChipStatus.text = order.status
                    fragmentOrderDetailsChipStatus.setChipBackgroundColorResource(order.orderStatusBackground)
                    fragmentOrderDetailsTvTimeValue.text = order.dateTime
                    fragmentOrderDetailsTvPickupMethodValue.text = order.pickupMethod
                    fragmentOrderDetailsTvDeferredTimeValue.text = order.deferredTime
                    fragmentOrderDetailsTvAddressValue.text = order.address
                    fragmentOrderDetailsTvCommentValue.text = order.comment
                    fragmentOrderDetailsTvDeliveryCostValue.text = order.deliveryCost
                    fragmentOrderDetailsTvOrderOldTotalCost.text = order.oldTotalCost
                    fragmentOrderDetailsTvOrderOldTotalCost.strikeOutText()
                    fragmentOrderDetailsTvOrderNewTotalCost.text = order.newTotalCost
                    fragmentOrderDetailsRvProductList.addItemDecoration(marginItemDecoration)
                    fragmentOrderDetailsRvProductList.adapter = orderProductAdapter
                    orderProductAdapter.submitList(order.orderProductList)

                    if (order.deferredTime.isNullOrEmpty()) {
                        fragmentOrderDetailsTvDeferredTimeValue.gone()
                        fragmentOrderDetailsTvDeferredTime.gone()
                    }
                    if (order.comment.isNullOrEmpty()) {
                        fragmentOrderDetailsTvCommentValue.gone()
                        fragmentOrderDetailsTvComment.gone()
                    }
                    if (!order.isDelivery) {
                        fragmentOrderDetailsTvDeliveryCostValue.gone()
                        fragmentOrderDetailsTvDeliveryCost.gone()
                    }
                }
            }
        }.startedLaunch()
    }

    override fun onDestroyView() {
        viewDataBinding.fragmentOrderDetailsRvProductList.adapter = null

        super.onDestroyView()
    }
}