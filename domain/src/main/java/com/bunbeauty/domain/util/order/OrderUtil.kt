package com.bunbeauty.domain.util.order

import com.bunbeauty.domain.R
import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.enums.OrderStatus.*
import com.bunbeauty.domain.model.Delivery
import com.bunbeauty.domain.model.order.Order
import com.bunbeauty.domain.model.product.OrderProduct
import com.bunbeauty.domain.model.product.ProductPosition
import com.bunbeauty.domain.util.product.IProductHelper
import javax.inject.Inject

class OrderUtil @Inject constructor(private val productHelper: IProductHelper) : IOrderUtil {

    override fun getDeliveryCost(order: Order, delivery: Delivery): Int {
        return getDeliveryCost(order.isDelivery, order.orderProductList, delivery)
    }

    override fun <T : ProductPosition> getDeliveryCost(
        isDelivery: Boolean,
        orderProductList: List<T>,
        delivery: Delivery
    ): Int {
        val orderCost = productHelper.getNewTotalCost(orderProductList)

        return if (isDelivery && orderCost < delivery.forFree) {
            delivery.cost
        } else {
            0
        }
    }

    override fun getOldOrderCost(order: Order, delivery: Delivery): Int? {
        return getOldOrderCost(order.isDelivery, order.orderProductList, delivery)
    }

    override fun getOldOrderCost(
        isDelivery: Boolean,
        orderProductList: List<OrderProduct>,
        delivery: Delivery
    ): Int? {
        val orderCost = productHelper.getOldTotalCost(orderProductList) ?: return null
        val deliveryCost = getDeliveryCost(isDelivery, orderProductList, delivery)

        return orderCost + deliveryCost
    }

    override fun getNewOrderCost(order: Order, delivery: Delivery): Int {
        return getNewOrderCost(order.isDelivery, order.orderProductList, delivery)
    }

    override fun <T : ProductPosition> getNewOrderCost(
        isDelivery: Boolean,
        orderProductList: List<T>,
        delivery: Delivery
    ): Int {
        val orderCost = productHelper.getNewTotalCost(orderProductList)
        val deliveryCost = getDeliveryCost(isDelivery, orderProductList, delivery)

        return orderCost + deliveryCost
    }

    override fun getProceeds(orderList: List<Order>, delivery: Delivery): Int {
        return orderList.sumOf { order ->
            getNewOrderCost(order, delivery)
        }
    }

    override fun getAverageCheck(orderList: List<Order>, delivery: Delivery): Int {
        val proceeds = getProceeds(orderList, delivery)

        return proceeds / orderList.size
    }

    override fun getBackgroundColor(status: OrderStatus): Int {
        return when (status) {
            NOT_ACCEPTED -> R.color.acceptedColor
            ACCEPTED -> R.color.acceptedColor
            PREPARING -> R.color.preparingColor
            SENT_OUT -> R.color.sentOutColor
            DONE -> R.color.doneColor
            DELIVERED -> R.color.deliveredColor
            CANCELED -> R.color.canceledColor
        }
    }

    override fun getOrderStepCount(status: OrderStatus): Int {
        return when (status) {
            NOT_ACCEPTED -> 1
            ACCEPTED -> 1
            PREPARING -> 2
            SENT_OUT -> 3
            DONE -> 3
            DELIVERED -> 4
            CANCELED -> 0
        }
    }
}