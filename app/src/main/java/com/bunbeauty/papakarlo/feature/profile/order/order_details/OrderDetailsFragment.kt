package com.bunbeauty.papakarlo.feature.profile.order.order_details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.decorator.MarginItemVerticalDecoration
import com.bunbeauty.papakarlo.common.delegates.argument
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.databinding.FragmentOrderDetailsBinding
import com.bunbeauty.papakarlo.extensions.gone
import com.bunbeauty.papakarlo.extensions.strikeOutText
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderDetailsFragment : BaseFragment(R.layout.fragment_order_details) {


    val orderProductAdapter: OrderProductAdapter by inject()

    val marginItemVerticalDecoration: MarginItemVerticalDecoration by inject()

    override val viewModel: OrderDetailsViewModel by viewModel()
    override val viewBinding by viewBinding(FragmentOrderDetailsBinding::bind)

    private val orderUuid: String by argument()


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeOrder(orderUuid)

        viewBinding.run {
            fragmentOrderDetailsRvProductList.addItemDecoration(marginItemVerticalDecoration)
            fragmentOrderDetailsRvProductList.adapter = orderProductAdapter

            viewModel.orderState.startedLaunch { state ->
                viewBinding.fragmentOrderDetailsMcvOrderDetails.toggleVisibility(state is State.Success)
                viewBinding.fragmentOrderDetailsRvProductList.toggleVisibility(state is State.Success)
                viewBinding.fragmentOrderDetailsVBlur.toggleVisibility(state is State.Success)
                viewBinding.fragmentOrderDetailsClBottomCost.toggleVisibility(state is State.Success)
                viewBinding.fragmentOrderDetailsPbLoading.toggleVisibility(state !is State.Success)

                if (state is State.Success) {
                    val order = state.data
                    fragmentOrderDetailsTvTimeValue.text = order.dateTime
                    fragmentOrderDetailsTvPickupMethodValue.text = order.pickupMethod
                    fragmentOrderDetailsTvDeferredTimeValue.text = order.deferredTime
                    fragmentOrderDetailsTvAddressValue.text = order.address
                    fragmentOrderDetailsTvCommentValue.text = order.comment
                    fragmentOrderDetailsTvDeliveryCostValue.text = order.deliveryCost

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
            viewModel.orderStatus.startedLaunch { orderStatusUI ->
                if (orderStatusUI != null) {
                    fragmentOrderDetailsPsvStatus.currentStep = orderStatusUI.stepCount
                    fragmentOrderDetailsChipStatus.text = orderStatusUI.name
                    fragmentOrderDetailsChipStatus.setChipBackgroundColorResource(orderStatusUI.background)
                }
            }
            viewModel.oldAmountToPay.startedLaunch { oldAmountToPay ->
                fragmentOrderDetailsTvOrderOldTotalCost.text = oldAmountToPay
                fragmentOrderDetailsTvOrderOldTotalCost.strikeOutText()
            }
            viewModel.newAmountToPay.startedLaunch { newAmountToPay ->
                fragmentOrderDetailsTvOrderNewTotalCost.text = newAmountToPay
            }
        }
    }

    override fun onDestroyView() {
        viewBinding.fragmentOrderDetailsRvProductList.adapter = null

        super.onDestroyView()
    }
}