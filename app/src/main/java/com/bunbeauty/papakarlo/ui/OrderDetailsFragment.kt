package com.bunbeauty.papakarlo.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.gone
import com.bunbeauty.domain.order.IOrderUtil
import com.bunbeauty.domain.product.IProductHelper
import com.bunbeauty.domain.string_helper.IStringHelper
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentOrderDetailsBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.OrderViewModel
import com.bunbeauty.papakarlo.ui.adapter.CartProductsAdapter
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class OrderDetailsFragment : BaseFragment<FragmentOrderDetailsBinding>() {

    override var layoutId = R.layout.fragment_order_details
    override val viewModel: OrderViewModel by viewModels { modelFactory }

    @Inject
    lateinit var stringHelper: IStringHelper

    @Inject
    lateinit var orderUtil: IOrderUtil

    @Inject
    lateinit var productHelper: IProductHelper

    @Inject
    lateinit var cartProductsAdapter: CartProductsAdapter

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
                            state.data!!.orderEntity.code
                        viewDataBinding.fragmentOrderDetailsTvTimeValue.text =
                            stringHelper.toStringTime(state.data!!.orderEntity)
                        viewDataBinding.fragmentOrderDetailsTvPickupMethodValue.text =
                            stringHelper.toStringIsDelivery(state.data!!.orderEntity)
                        viewDataBinding.fragmentOrderDetailsTvDeferredTimeValue.text =
                            state.data!!.orderEntity.deferredTime
                        viewDataBinding.fragmentOrderDetailsTvAddressValue.text =
                            stringHelper.toString(state.data!!.orderEntity.address)
                        viewDataBinding.fragmentOrderDetailsTvCommentValue.text =
                            state.data!!.orderEntity.comment
                        viewDataBinding.fragmentOrderDetailsTvDeliveryCostValue.text =
                            stringHelper.getDeliveryString(
                                orderUtil.getDeliveryCost(
                                    state.data!!,
                                    viewModel.delivery
                                )
                            )
                        viewDataBinding.fragmentOrderDetailsTvOrderOldTotalCost.text =
                            stringHelper.getCostString(
                                orderUtil.getOldOrderCost(
                                    state.data!!,
                                    viewModel.delivery
                                )
                            )
                        viewDataBinding.fragmentOrderDetailsTvOrderNewTotalCost.text =
                            stringHelper.getCostString(
                                orderUtil.getNewOrderCost(
                                    state.data!!, viewModel.delivery
                                )
                            )
                        cartProductsAdapter.canBeChanged = false
                        viewDataBinding.fragmentOrderDetailsRvProductList.adapter =
                            cartProductsAdapter
                        cartProductsAdapter.setItemList(state.data!!.cartProducts)

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
                    }
                }
                else -> Unit
            }
        }.launchWhenStarted(lifecycleScope)
    }
}