package com.bunbeauty.papakarlo.common.mapper.order

import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.order.LightOrder
import com.bunbeauty.domain.model.order.Order
import com.bunbeauty.papakarlo.feature.profile.order.order_details.OrderProductItem
import com.bunbeauty.papakarlo.feature.profile.order.order_details.OrderStatusUI
import com.bunbeauty.papakarlo.feature.profile.order.order_details.OrderUI
import com.bunbeauty.papakarlo.feature.profile.order.order_list.OrderItem
import com.bunbeauty.papakarlo.util.color.IColorUtil
import com.bunbeauty.papakarlo.util.string.IStringUtil
import javax.inject.Inject

class OrderUIMapper @Inject constructor(
    private val stringUtil: IStringUtil,
    private val colorUtil: IColorUtil,
) : IOrderUIMapper {

    override fun toItem(order: LightOrder): OrderItem {
        return OrderItem(
            uuid = order.uuid,
            orderStatus = stringUtil.getOrderStatusName(order.status),
            orderColorResource = colorUtil.getOrderStatusColor(order.status),
            code = order.code,
            dateTime = stringUtil.getDateTimeString(order.dateTime)
        )
    }

    override fun toOrderUI(order: Order): OrderUI {
        return OrderUI(
            code = order.code,
            dateTime = stringUtil.getDateTimeString(order.dateTime),
            pickupMethod = stringUtil.getPickupMethodString(order.isDelivery),
            deferredTime = order.deferredTime?.let { deferredTime ->
                stringUtil.getTimeString(deferredTime)
            },
            address = order.address,
            comment = order.comment,
            deliveryCost = stringUtil.getCostString(order.deliveryCost),
            orderProductList = order.orderProductList.map { orderProduct ->
                OrderProductItem(
                    uuid = orderProduct.uuid,
                    name = orderProduct.product.name,
                    newCost = stringUtil.getCostString(orderProduct.product.newPrice),
                    oldCost = stringUtil.getCostString(orderProduct.product.oldPrice),
                    photoLink = orderProduct.product.photoLink,
                    count = stringUtil.getCountString(orderProduct.count),
                )
            },
            isDelivery = order.isDelivery,
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