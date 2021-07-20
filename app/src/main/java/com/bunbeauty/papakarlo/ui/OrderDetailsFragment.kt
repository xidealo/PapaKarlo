package com.bunbeauty.papakarlo.ui

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.common.State
import com.bunbeauty.domain.util.order.IOrderUtil
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.domain.util.string_helper.IStringUtil
import com.bunbeauty.papakarlo.databinding.FragmentOrderDetailsBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.gone
import com.bunbeauty.papakarlo.extensions.startedLaunch
import com.bunbeauty.papakarlo.extensions.visible
import com.bunbeauty.papakarlo.presentation.profile.OrderDetailsViewModel
import com.bunbeauty.papakarlo.ui.adapter.CartProductAdapter
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class OrderDetailsFragment : BaseFragment<FragmentOrderDetailsBinding>() {

    override val viewModel: OrderDetailsViewModel by viewModels { modelFactory }

    @Inject
    lateinit var stringUtil: IStringUtil

    @Inject
    lateinit var orderUtil: IOrderUtil

    @Inject
    lateinit var productHelper: IProductHelper

    @Inject
    lateinit var cartProductAdapter: CartProductAdapter

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getOrder(OrderDetailsFragmentArgs.fromBundle(requireArguments()).orderUuid)
        viewModel.orderState.onEach { state ->
            when (state) {
                is State.Success -> {
                    if (state.data != null) {
                        viewDataBinding.fragmentOrderDetailsTvCode.text =
                            "Заказ ${state.data!!.orderEntity.code}"
                        viewDataBinding.fragmentOrderDetailsChipStatus.text =
                            stringUtil.toStringOrderStatus(state.data!!.orderEntity.orderStatus)
                        viewDataBinding.fragmentOrderDetailsChipStatus.setChipBackgroundColorResource(
                            orderUtil.getBackgroundColor(
                                state.data!!.orderEntity.orderStatus
                            )
                        )
                        viewDataBinding.fragmentOrderDetailsTvTimeValue.text =
                            stringUtil.toStringTime(state.data!!.orderEntity)
                        viewDataBinding.fragmentOrderDetailsTvPickupMethodValue.text =
                            stringUtil.toStringIsDelivery(state.data!!.orderEntity)
                        viewDataBinding.fragmentOrderDetailsTvDeferredTimeValue.text =
                            state.data!!.orderEntity.deferredTime
                        viewDataBinding.fragmentOrderDetailsTvAddressValue.text =
                            stringUtil.toString(state.data!!.orderEntity.address)
                        viewDataBinding.fragmentOrderDetailsTvCommentValue.text =
                            state.data!!.orderEntity.comment
                        viewDataBinding.fragmentOrderDetailsTvDeliveryCostValue.text =
                            stringUtil.getDeliveryString(
                                orderUtil.getDeliveryCost(
                                    state.data!!,
                                    viewModel.delivery
                                )
                            )
                        viewDataBinding.fragmentOrderDetailsTvOrderOldTotalCost.text =
                            stringUtil.getCostString(
                                orderUtil.getOldOrderCost(
                                    state.data!!,
                                    viewModel.delivery
                                )
                            )
                        viewDataBinding.fragmentOrderDetailsTvOrderOldTotalCost.paintFlags =
                            viewDataBinding.fragmentOrderDetailsTvOrderOldTotalCost.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                        viewDataBinding.fragmentOrderDetailsTvOrderNewTotalCost.text =
                            stringUtil.getCostString(
                                orderUtil.getNewOrderCost(
                                    state.data!!, viewModel.delivery
                                )
                            )
                        cartProductAdapter.canBeChanged = false
                        viewDataBinding.fragmentOrderDetailsRvProductList.adapter =
                            cartProductAdapter
                        val mappedCartProducts =
                            viewModel.getCartProductModel(state.data!!.cartProducts)
                        cartProductAdapter.submitList(mappedCartProducts)

                        if (state.data!!.orderEntity.deferredTime.isEmpty()) {
                            viewDataBinding.fragmentOrderDetailsTvDeferredTimeValue.gone()
                            viewDataBinding.fragmentOrderDetailsTvDeferredTime.gone()
                        }
                        if (state.data!!.orderEntity.comment.isEmpty()) {
                            viewDataBinding.fragmentOrderDetailsTvCommentValue.gone()
                            viewDataBinding.fragmentOrderDetailsTvComment.gone()
                        }
                        if (!state.data!!.orderEntity.isDelivery) {
                            viewDataBinding.fragmentOrderDetailsTvDeliveryCostValue.gone()
                            viewDataBinding.fragmentOrderDetailsTvDeliveryCost.gone()
                        }
                        viewDataBinding.fragmentOrderDetailsClMain.visible()
                        viewDataBinding.fragmentOrderDetailsClBottomCost.visible()
                        viewDataBinding.fragmentOrderDetailsPbLoading.gone()
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
}