package com.bunbeauty.domain.util.order

import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.local.Delivery
import com.bunbeauty.domain.model.local.order.Order
import com.bunbeauty.domain.R
import com.bunbeauty.domain.util.product.IProductHelper
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

}