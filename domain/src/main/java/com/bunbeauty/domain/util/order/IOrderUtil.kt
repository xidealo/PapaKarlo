package com.bunbeauty.domain.util.order

import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.Delivery
import com.bunbeauty.domain.model.Order
import com.bunbeauty.domain.model.product.OrderProduct
import com.bunbeauty.domain.model.product.ProductPosition

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
    fun getOrderStepCount(status: OrderStatus): Int
}