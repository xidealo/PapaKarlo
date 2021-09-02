package com.bunbeauty.papakarlo.presentation.profile

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateNullableSuccess
import com.bunbeauty.domain.model.ui.OrderUI
import com.bunbeauty.domain.model.ui.product.OrderProduct
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.OrderRepo
import com.bunbeauty.domain.util.order.IOrderUtil
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.presentation.util.string.IStringUtil
import com.bunbeauty.presentation.view_model.base.adapter.CartProductItem
import com.bunbeauty.presentation.view_model.base.adapter.OrderDetailsItem
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class OrderDetailsViewModel @Inject constructor(
    private val orderRepo: OrderRepo,
    private val dataStoreRepo: DataStoreRepo,
    private val stringUtil: IStringUtil,
    private val productHelper: IProductHelper,
    private val orderUtil: IOrderUtil,
) : BaseViewModel() {

    private val mutableOrderState: MutableStateFlow<State<OrderDetailsItem?>> =
        MutableStateFlow(State.Loading())
    val orderState: StateFlow<State<OrderDetailsItem?>> = mutableOrderState.asStateFlow()

    fun getOrder(orderUuid: String) {
        orderRepo.observeOrderByUuid(orderUuid).onEach { order ->
            if (order == null) {
                mutableOrderState.value = State.Empty()
            } else {
                mutableOrderState.value = toItem(order).toStateNullableSuccess()
            }
        }.launchIn(viewModelScope)
    }

    private fun toItem(orderProduct: OrderProduct): CartProductItem {
        val newCost = productHelper.getCartProductNewCost(orderProduct)
        val oldCost = productHelper.getCartProductOldCost(orderProduct)
        val newCostString = stringUtil.getCostString(newCost)
        val oldCostString = stringUtil.getCostString(oldCost)

        return CartProductItem(
            uuid = orderProduct.uuid,
            name = orderProduct.menuProduct.name,
            newCost = newCostString,
            oldCost = oldCostString,
            photoLink = orderProduct.menuProduct.photoLink,
            count = orderProduct.count,
            menuProductUuid = orderProduct.menuProduct.uuid
        )
    }

    private fun toItem(order: OrderUI): OrderDetailsItem {
        val delivery = runBlocking {
            dataStoreRepo.delivery.first()
        }
        return OrderDetailsItem(
            code = order.code,
            orderStatus = stringUtil.toStringOrderStatus(order.orderStatus),
            orderStatusBackground = orderUtil.getBackgroundColor(order.orderStatus),
            orderStatusActiveLine = orderUtil.getActiveLineCount(order.orderStatus),
            time = stringUtil.toStringTime(order.time),
            pickupMethod = stringUtil.toStringIsDelivery(order.isDelivery),
            deferredTime = order.deferredTime ?: "",
            address = stringUtil.getUserAddressString(order.userAddress),
            comment = order.comment ?: "",
            deliveryCost = stringUtil.getDeliveryString(orderUtil.getDeliveryCost(order, delivery)),
            oldTotalCost = stringUtil.getCostString(orderUtil.getOldOrderCost(order, delivery)),
            newTotalCost = stringUtil.getCostString(orderUtil.getNewOrderCost(order, delivery)),
            cartProducts = order.orderProductList.map(::toItem),
            isDelivery = order.isDelivery,
        )
    }
}