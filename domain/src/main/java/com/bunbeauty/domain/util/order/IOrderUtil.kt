package com.bunbeauty.domain.util.order

import com.bunbeauty.domain.enums.ActiveLines
import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.ui.CartProduct
import com.bunbeauty.domain.model.ui.Delivery
import com.bunbeauty.domain.model.entity.order.Order

interface IOrderUtil {

    fun getDeliveryCost(order: Order, delivery: Delivery): Int
    fun getDeliveryCost(
        isDelivery: Boolean,
        cartProducts: List<CartProduct>,
        delivery: Delivery
    ): Int

    fun getOldOrderCost(order: Order, delivery: Delivery): Int?
    fun getOldOrderCost(
        isDelivery: Boolean,
        cartProducts: List<CartProduct>,
        delivery: Delivery
    ): Int?

    fun getNewOrderCost(order: Order, delivery: Delivery): Int
    fun getNewOrderCost(
        isDelivery: Boolean,
        cartProducts: List<CartProduct>,
        delivery: Delivery
    ): Int

    fun getProceeds(orderList: List<Order>, delivery: Delivery): Int
    fun getAverageCheck(orderList: List<Order>, delivery: Delivery): Int
    fun getBackgroundColor(status: OrderStatus): Int
    fun getActiveLineCount(status: OrderStatus): ActiveLines
}