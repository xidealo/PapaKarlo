package com.bunbeauty.domain.util.order

import com.bunbeauty.domain.R
import com.bunbeauty.domain.enums.ActiveLines
import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.ui.Delivery
import com.bunbeauty.domain.model.ui.product.OrderProduct
import com.bunbeauty.domain.model.ui.Order
import com.bunbeauty.domain.model.ui.product.ProductPosition
import com.bunbeauty.domain.util.product.IProductHelper
import javax.inject.Inject

class OrderUtil @Inject constructor(private val productHelper: IProductHelper) : IOrderUtil {

    override fun getDeliveryCost(order: Order, delivery: Delivery): Int {
        return getDeliveryCost(order.isDelivery, order.orderProductList, delivery)
    }

    override fun <T: ProductPosition> getDeliveryCost(
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

    override fun <T: ProductPosition> getNewOrderCost(
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
            OrderStatus.NOT_ACCEPTED -> R.color.notAcceptedColor
            OrderStatus.ACCEPTED -> R.color.acceptedColor
            OrderStatus.PREPARING -> R.color.preparingColor
            OrderStatus.SENT_OUT -> R.color.sentOutColor
            OrderStatus.DONE -> R.color.doneColor
            OrderStatus.DELIVERED -> R.color.deliveredColor
            else -> R.color.notAcceptedColor
        }
    }

    override fun getActiveLineCount(status: OrderStatus): ActiveLines {
        return when (status) {
            OrderStatus.NOT_ACCEPTED -> ActiveLines.ZERO_LINE
            OrderStatus.ACCEPTED -> ActiveLines.ONE_LINE
            OrderStatus.PREPARING -> ActiveLines.TWO_LINE
            OrderStatus.SENT_OUT -> ActiveLines.THREE_LINE
            OrderStatus.DONE -> ActiveLines.THREE_LINE
            OrderStatus.DELIVERED -> ActiveLines.FOUR_LINE
            else -> ActiveLines.ZERO_LINE
        }
    }

    override fun getOrderStepCount(status: OrderStatus): Int {
        return when (status) {
            OrderStatus.NOT_ACCEPTED -> 0
            OrderStatus.ACCEPTED -> 1
            OrderStatus.PREPARING -> 2
            OrderStatus.SENT_OUT -> 3
            OrderStatus.DONE -> 3
            OrderStatus.DELIVERED -> 4
            OrderStatus.CANCELED -> 0
        }
    }
}