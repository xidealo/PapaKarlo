package com.bunbeauty.papakarlo.ui.fragment.profile.order

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
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
import com.bunbeauty.papakarlo.ui.decorator.MarginItemVerticalDecoration
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class OrderDetailsFragment : BaseFragment(R.layout.fragment_order_details) {

    @Inject
    lateinit var orderProductAdapter: OrderProductAdapter

    @Inject
    lateinit var marginItemVerticalDecoration: MarginItemVerticalDecoration

    override val viewModel: OrderDetailsViewModel by viewModels { viewModelFactory }
    override val viewBinding by viewBinding(FragmentOrderDetailsBinding::bind)

    private val orderUuid: String by argument()

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getOrder(orderUuid)
        viewModel.orderState.onEach { state ->
            viewBinding.fragmentOrderDetailsMcvOrderDetails.toggleVisibility(state is State.Success)
            viewBinding.fragmentOrderDetailsRvProductList.toggleVisibility(state is State.Success)
            viewBinding.fragmentOrderDetailsVBlur.toggleVisibility(state is State.Success)
            viewBinding.fragmentOrderDetailsClBottomCost.toggleVisibility(state is State.Success)
            viewBinding.fragmentOrderDetailsPbLoading.toggleVisibility(state !is State.Success)

            if (state is State.Success) {
                val order = state.data

                viewBinding.run {
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
                    fragmentOrderDetailsRvProductList.addItemDecoration(marginItemVerticalDecoration)
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
        viewBinding.fragmentOrderDetailsRvProductList.adapter = null

        super.onDestroyView()
    }
}