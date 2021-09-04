package com.bunbeauty.domain.util.order

import com.bunbeauty.domain.enums.ActiveLines
import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.ui.Delivery
import com.bunbeauty.domain.model.ui.product.OrderProduct
import com.bunbeauty.domain.model.ui.Order
import com.bunbeauty.domain.model.ui.product.ProductPosition

interface IOrderUtil {

    fun getDeliveryCost(order: Order, delivery: Delivery): Int
    fun <T: ProductPosition> getDeliveryCost(
        isDelivery: Boolean,
        orderProductList: List<T>,
        delivery: Delivery
    ): Int

    fun getOldOrderCost(order: Order, delivery: Delivery): Int?
    fun getOldOrderCost(
        isDelivery: Boolean,
        orderProductList: List<OrderProduct>,
        delivery: Delivery
    ): Int?

    fun getNewOrderCost(order: Order, delivery: Delivery): Int
    fun <T: ProductPosition> getNewOrderCost(
        isDelivery: Boolean,
        orderProductList: List<T>,
        delivery: Delivery
    ): Int

    fun getProceeds(orderList: List<Order>, delivery: Delivery): Int
    fun getAverageCheck(orderList: List<Order>, delivery: Delivery): Int
    fun getBackgroundColor(status: OrderStatus): Int
    fun getActiveLineCount(status: OrderStatus): ActiveLines
    fun getOrderStepCount(status: OrderStatus): Int
}