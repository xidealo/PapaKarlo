package com.bunbeauty.domain.util.order

import com.bunbeauty.domain.R
import com.bunbeauty.domain.enums.ActiveLines
import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.ui.CartProduct
import com.bunbeauty.domain.model.ui.Delivery
import com.bunbeauty.domain.model.entity.order.Order
import com.bunbeauty.domain.util.product.IProductHelper
import javax.inject.Inject

class OrderUtil @Inject constructor(
    private val productHelper: IProductHelper
) : IOrderUtil {

    override fun getDeliveryCost(order: Order, delivery: Delivery): Int {
        return getDeliveryCost(order.orderEntity.isDelivery, order.cartProducts, delivery)
    }

    override fun getDeliveryCost(
        isDelivery: Boolean,
        cartProducts: List<CartProduct>,
        delivery: Delivery
    ): Int {
        val orderCost = productHelper.getNewTotalCost(cartProducts)

        return if (isDelivery && orderCost < delivery.forFree) {
            delivery.cost
        } else {
            0
        }
    }

    override fun getOldOrderCost(order: Order, delivery: Delivery): Int? {
        return getOldOrderCost(order.orderEntity.isDelivery, order.cartProducts, delivery)
    }

    override fun getOldOrderCost(
        isDelivery: Boolean,
        cartProducts: List<CartProduct>,
        delivery: Delivery
    ): Int? {
        val orderCost = productHelper.getOldTotalCost(cartProducts) ?: return null
        val deliveryCost = getDeliveryCost(isDelivery, cartProducts, delivery)

        return orderCost + deliveryCost
    }

    override fun getNewOrderCost(order: Order, delivery: Delivery): Int {
        return getNewOrderCost(order.orderEntity.isDelivery, order.cartProducts, delivery)
    }

    override fun getNewOrderCost(
        isDelivery: Boolean,
        cartProducts: List<CartProduct>,
        delivery: Delivery
    ): Int {
        val orderCost = productHelper.getNewTotalCost(cartProducts)
        val deliveryCost = getDeliveryCost(isDelivery, cartProducts, delivery)

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
}