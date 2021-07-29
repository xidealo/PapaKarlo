package com.bunbeauty.papakarlo.presentation.profile

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateNullableSuccess
import com.bunbeauty.domain.model.ui.CartProduct
import com.bunbeauty.domain.model.entity.order.Order
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.OrderRepo
import com.bunbeauty.domain.util.order.IOrderUtil
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.domain.util.string_helper.IStringUtil
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
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

    private val _orderState: MutableStateFlow<State<OrderDetailsItem?>> =
        MutableStateFlow(State.Loading())
    val orderState: StateFlow<State<OrderDetailsItem?>>
        get() = _orderState.asStateFlow()

    fun getOrder(orderUuid: String) {
        orderRepo.getOrderWithCartProducts(orderUuid).onEach { order ->
            if (order != null)
                _orderState.value = toItem(order).toStateNullableSuccess()
            else
                _orderState.value = State.Empty()
        }.launchIn(viewModelScope)
    }

    private fun toItem(cartProduct: CartProduct): CartProductItem {
        val newCost = productHelper.getCartProductNewCost(cartProduct)
        val oldCost = productHelper.getCartProductOldCost(cartProduct)
        val newCostString = stringUtil.getCostString(newCost)
        val oldCostString = stringUtil.getCostString(oldCost)

        return CartProductItem(
            uuid = cartProduct.uuid,
            name = cartProduct.menuProduct.name,
            newCost = newCostString,
            oldCost = oldCostString,
            photoLink = cartProduct.menuProduct.photoLink,
            count = cartProduct.count,
            menuProductUuid = cartProduct.menuProduct.uuid
        )
    }

    private fun toItem(order: Order): OrderDetailsItem {
        val delivery = runBlocking {
            dataStoreRepo.delivery.first()
        }
        return OrderDetailsItem(
            code = order.orderEntity.code,
            orderStatus = stringUtil.toStringOrderStatus(order.orderEntity.orderStatus),
            orderStatusBackground = orderUtil.getBackgroundColor(order.orderEntity.orderStatus),
            orderStatusActiveLine = orderUtil.getActiveLineCount(order.orderEntity.orderStatus),
            time = stringUtil.toStringTime(order.orderEntity),
            pickupMethod = stringUtil.toStringIsDelivery(order.orderEntity),
            deferredTime = order.orderEntity.deferredTime ?: "",
            address = order.orderEntity.userAddressUuid ?: "",
            comment = order.orderEntity.comment ?: "",
            deliveryCost = stringUtil.getDeliveryString(
                orderUtil.getDeliveryCost(
                    order,
                    delivery
                )
            ),
            oldTotalCost = stringUtil.getCostString(
                orderUtil.getOldOrderCost(
                    order,
                    delivery
                )
            ),
            newTotalCost = stringUtil.getCostString(
                orderUtil.getNewOrderCost(
                    order,
                    delivery
                )
            ),
            cartProducts = order.cartProducts.map(::toItem),
            isDelivery = order.orderEntity.isDelivery,
        )
    }
}