package com.bunbeauty.papakarlo.presentation.profile

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateSuccess
import com.bunbeauty.domain.model.ui.Delivery
import com.bunbeauty.domain.model.ui.Order
import com.bunbeauty.domain.model.ui.product.OrderProduct
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.OrderRepo
import com.bunbeauty.domain.util.order.IOrderUtil
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.presentation.item.OrderProductItem
import com.bunbeauty.presentation.util.string.IStringUtil
import com.bunbeauty.presentation.model.OrderUI
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class OrderDetailsViewModel @Inject constructor(
    private val orderRepo: OrderRepo,
    private val dataStoreRepo: DataStoreRepo,
    private val stringUtil: IStringUtil,
    private val productHelper: IProductHelper,
    private val orderUtil: IOrderUtil,
) : BaseViewModel() {

    private val mutableOrderState: MutableStateFlow<State<OrderUI>> =
        MutableStateFlow(State.Loading())
    val orderState: StateFlow<State<OrderUI>> = mutableOrderState.asStateFlow()

    fun getOrder(orderUuid: String) {
        orderRepo.observeOrderByUuid(orderUuid).onEach { order ->
            if (order == null) {
                mutableOrderState.value = State.Empty()
            } else {
                val delivery = dataStoreRepo.delivery.first()
                mutableOrderState.value = order.toUI(delivery).toStateSuccess()
            }
        }.launchIn(viewModelScope)
    }

    private fun Order.toUI(delivery: Delivery): OrderUI {
        return OrderUI(
            code = code,
            stepCount = orderUtil.getOrderStepCount(orderStatus),
            status = stringUtil.toStringOrderStatus(orderStatus),
            orderStatusBackground = orderUtil.getBackgroundColor(orderStatus),
            time = stringUtil.toStringTime(time),
            pickupMethod = stringUtil.toStringIsDelivery(isDelivery),
            deferredTime = deferredTime ?: "",
            address = stringUtil.getUserAddressString(userAddress),
            comment = comment ?: "",
            deliveryCost = stringUtil.getDeliveryString(orderUtil.getDeliveryCost(this, delivery)),
            oldTotalCost = stringUtil.getCostString(orderUtil.getOldOrderCost(this, delivery)),
            newTotalCost = stringUtil.getCostString(orderUtil.getNewOrderCost(this, delivery)),
            orderProductList = orderProductList.map { orderProduct ->
                orderProduct.toItem()
            },
            isDelivery = isDelivery,
        )
    }

    private fun OrderProduct.toItem(): OrderProductItem {
        val newCost = productHelper.getCartProductNewCost(this)
        val oldCost = productHelper.getCartProductOldCost(this)
        val newCostString = stringUtil.getCostString(newCost)
        val oldCostString = stringUtil.getCostString(oldCost)
        val countString = stringUtil.getCountString(count)

        return OrderProductItem(
            uuid = uuid,
            name = menuProduct.name,
            newCost = newCostString,
            oldCost = oldCostString,
            photoLink = menuProduct.photoLink,
            count = countString
        )
    }
}