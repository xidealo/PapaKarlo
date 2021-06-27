package com.bunbeauty.domain.order

import com.bunbeauty.data.enums.OrderStatus
import com.bunbeauty.data.model.Delivery
import com.bunbeauty.data.model.order.Order

interface IOrderUtil {

    fun getDeliveryCost(order: Order, delivery: Delivery): Int
    fun getOldOrderCost(order: Order, delivery: Delivery): Int?
    fun getNewOrderCost(order: Order, delivery: Delivery): Int

    fun getProceeds(orderList: List<Order>, delivery: Delivery): Int
    fun getAverageCheck(orderList: List<Order>, delivery: Delivery): Int
    fun getBackgroundColor(status: OrderStatus): Int
}