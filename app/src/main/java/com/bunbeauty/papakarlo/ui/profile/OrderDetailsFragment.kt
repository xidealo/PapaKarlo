package com.bunbeauty.papakarlo.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.common.State
import com.bunbeauty.papakarlo.databinding.FragmentOrderDetailsBinding
import com.bunbeauty.papakarlo.delegates.argument
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.gone
import com.bunbeauty.papakarlo.extensions.startedLaunch
import com.bunbeauty.papakarlo.extensions.strikeOutText
import com.bunbeauty.papakarlo.extensions.visible
import com.bunbeauty.papakarlo.presentation.profile.OrderDetailsViewModel
import com.bunbeauty.papakarlo.ui.adapter.CartProductAdapter
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class OrderDetailsFragment : BaseFragment<FragmentOrderDetailsBinding>() {

    @Inject
    lateinit var cartProductAdapter: CartProductAdapter

    @Inject
    lateinit var resourcesProvider: IResourcesProvider

    override val viewModel: OrderDetailsViewModel by viewModels { modelFactory }

    private val orderUuid: String by argument()

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getOrder(orderUuid)
        viewModel.orderState.onEach { state ->
            when (state) {
                is State.Success -> {
                    val order = state.data

                    viewDataBinding.run {
                        fragmentOrderDetailsPsvStatus.currentStep = order.stepCount
                        fragmentOrderDetailsChipStatus.text = order.status
                        fragmentOrderDetailsChipStatus.setChipBackgroundColorResource(order.orderStatusBackground)
                        fragmentOrderDetailsTvTimeValue.text = order.time
                        fragmentOrderDetailsTvPickupMethodValue.text = order.pickupMethod
                        fragmentOrderDetailsTvDeferredTimeValue.text = order.deferredTime
                        fragmentOrderDetailsTvAddressValue.text = order.address
                        fragmentOrderDetailsTvCommentValue.text = order.comment
                        fragmentOrderDetailsTvDeliveryCostValue.text = order.deliveryCost
                        fragmentOrderDetailsTvOrderOldTotalCost.text = order.oldTotalCost
                        fragmentOrderDetailsTvOrderOldTotalCost.strikeOutText()
                        fragmentOrderDetailsTvOrderNewTotalCost.text = order.newTotalCost
                        cartProductAdapter.canBeChanged = false
                        fragmentOrderDetailsRvProductList.adapter = cartProductAdapter
                        cartProductAdapter.submitList(order.cartProducts)

                        if (order.deferredTime.isEmpty()) {
                            fragmentOrderDetailsTvDeferredTimeValue.gone()
                            fragmentOrderDetailsTvDeferredTime.gone()
                        }
                        if (order.comment.isEmpty()) {
                            fragmentOrderDetailsTvCommentValue.gone()
                            fragmentOrderDetailsTvComment.gone()
                        }
                        if (!order.isDelivery) {
                            fragmentOrderDetailsTvDeliveryCostValue.gone()
                            fragmentOrderDetailsTvDeliveryCost.gone()
                        }
                        fragmentOrderDetailsClMain.visible()
                        fragmentOrderDetailsClBottomCost.visible()
                        fragmentOrderDetailsPbLoading.gone()
                    }
                }
                is State.Loading -> {
                    viewDataBinding.fragmentOrderDetailsClMain.gone()
                    viewDataBinding.fragmentOrderDetailsClBottomCost.gone()
                    viewDataBinding.fragmentOrderDetailsPbLoading.visible()
                }
                else -> Unit
            }
        }.startedLaunch(viewLifecycleOwner)
    }

    override fun onDestroyView() {
        viewDataBinding.fragmentOrderDetailsRvProductList.adapter = null

        super.onDestroyView()
    }
}