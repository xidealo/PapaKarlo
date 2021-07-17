package com.bunbeauty.domain.util.order

import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.local.Delivery
import com.bunbeauty.domain.model.local.order.Order

interface IOrderUtil {

    fun getDeliveryCost(order: Order, delivery: Delivery): Int
    fun getOldOrderCost(order: Order, delivery: Delivery): Int?
    fun getNewOrderCost(order: Order, delivery: Delivery): Int

    fun getProceeds(orderList: List<Order>, delivery: Delivery): Int
    fun getAverageCheck(orderList: List<Order>, delivery: Delivery): Int
    fun getBackgroundColor(status: OrderStatus): Int
}