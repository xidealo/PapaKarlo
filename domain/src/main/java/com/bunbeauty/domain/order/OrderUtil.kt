package com.bunbeauty.domain.order

import com.bunbeauty.data.model.Delivery
import com.bunbeauty.data.model.order.Order
import com.bunbeauty.domain.product.IProductHelper
import javax.inject.Inject

class OrderUtil @Inject constructor(
    private val productHelper: IProductHelper
) : IOrderUtil {

    override fun getDeliveryCost(order: Order, delivery: Delivery): Int {
        val orderCost = productHelper.getNewTotalCost(order.cartProducts)

        return if (order.orderEntity.isDelivery && orderCost < delivery.forFree) {
            delivery.cost
        } else {
            0
        }
    }

    override fun getOldOrderCost(order: Order, delivery: Delivery): Int? {
        val orderCost = productHelper.getOldTotalCost(order.cartProducts) ?: return null
        val deliveryCost = getDeliveryCost(order, delivery)

        return orderCost + deliveryCost
    }

    override fun getNewOrderCost(order: Order, delivery: Delivery): Int {
        val orderCost = productHelper.getNewTotalCost(order.cartProducts)
        val deliveryCost = getDeliveryCost(order, delivery)

        return orderCost + deliveryCost
    }

    override fun getProceeds(orderList: List<Order>, delivery: Delivery): Int {
        return orderList.sumBy { order ->
            getNewOrderCost(order, delivery)
        }
    }

    override fun getAverageCheck(orderList: List<Order>, delivery: Delivery): Int {
        val proceeds = getProceeds(orderList, delivery)

        return proceeds / orderList.size
    }
}