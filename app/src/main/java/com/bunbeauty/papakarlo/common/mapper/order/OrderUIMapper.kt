package com.bunbeauty.papakarlo.common.mapper.order

import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.order.LightOrder
import com.bunbeauty.domain.model.order.OrderDetails
import com.bunbeauty.papakarlo.feature.profile.order.order_details.OrderProductItem
import com.bunbeauty.papakarlo.feature.profile.order.order_details.OrderStatusUI
import com.bunbeauty.papakarlo.feature.profile.order.order_details.OrderUI
import com.bunbeauty.papakarlo.feature.profile.order.order_list.OrderItem
import javax.inject.Inject

class OrderUIMapper @Inject constructor(
    private val stringUtil: com.bunbeauty.papakarlo.util.string.IStringUtil,
    private val colorUtil: com.bunbeauty.papakarlo.util.color.IColorUtil,
) : IOrderUIMapper {

    override fun toItem(order: LightOrder): OrderItem {
        return OrderItem(
            uuid = order.uuid,
            orderStatus = stringUtil.getOrderStatusName(order.status),
            orderColorResource = colorUtil.getOrderStatusColor(order.status),
            code = order.code,
            dateTime = order.dateTime
        )
    }

    override fun toOrderUI(orderDetails: OrderDetails): OrderUI {
        return OrderUI(
            code = orderDetails.code,
            dateTime = orderDetails.dateTime,
            pickupMethod = stringUtil.getPickupMethodString(orderDetails.isDelivery),
            deferredTime = orderDetails.deferredTime,
            address = orderDetails.address,
            comment = orderDetails.comment,
            deliveryCost = stringUtil.getCostString(orderDetails.deliveryCost),
            orderProductList = orderDetails.orderProductList.map { orderProduct ->
                OrderProductItem(
                    uuid = orderProduct.uuid,
                    name = orderProduct.product.name,
                    newCost = stringUtil.getCostString(orderProduct.product.newPrice),
                    oldCost = stringUtil.getCostString(orderProduct.product.oldPrice),
                    photoLink = orderProduct.product.photoLink,
                    count = stringUtil.getCountString(orderProduct.count),
                )
            },
            isDelivery = orderDetails.isDelivery,
        )
    }

    override fun toOrderStatusUI(orderStatus: OrderStatus): OrderStatusUI {
        return OrderStatusUI(
            name = stringUtil.getOrderStatusName(orderStatus),
            stepCount = toOrderStepCount(orderStatus),
            background = colorUtil.getOrderStatusColor(orderStatus)
        )
    }

    fun toOrderStepCount(status: OrderStatus): Int {
        return when (status) {
            OrderStatus.NOT_ACCEPTED -> 1
            OrderStatus.ACCEPTED -> 1
            OrderStatus.PREPARING -> 2
            OrderStatus.SENT_OUT -> 3
            OrderStatus.DONE -> 3
            OrderStatus.DELIVERED -> 4
            OrderStatus.CANCELED -> 0
        }
    }
}