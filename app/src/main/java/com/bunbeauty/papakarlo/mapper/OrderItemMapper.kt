package com.bunbeauty.papakarlo.mapper

import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.feature.order.model.OrderItem
import com.bunbeauty.papakarlo.feature.order.model.OrderProductItem
import com.bunbeauty.papakarlo.feature.order.model.OrderUI
import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.domain.model.order.OrderWithAmounts

class OrderItemMapper(
    private val stringUtil: IStringUtil
) {

    fun toItem(order: LightOrder): OrderItem {
        return OrderItem(
            uuid = order.uuid,
            status = order.status,
            statusName = stringUtil.getOrderStatusName(order.status),
            code = order.code,
            dateTime = stringUtil.getDateTimeString(order.dateTime)
        )
    }

    fun toOrderUI(orderWithAmounts: OrderWithAmounts): OrderUI {
        return OrderUI(
            code = orderWithAmounts.code,
            status = orderWithAmounts.status,
            statusName = stringUtil.getOrderStatusName(orderWithAmounts.status),
            dateTime = stringUtil.getDateTimeString(orderWithAmounts.dateTime),
            pickupMethod = stringUtil.getPickupMethodString(orderWithAmounts.isDelivery),
            deferredTimeHintStringId = if (orderWithAmounts.isDelivery) {
                R.string.msg_order_details_deferred_time_delivery
            } else {
                R.string.msg_order_details_deferred_time_pickup
            },
            deferredTime = orderWithAmounts.deferredTime?.let { deferredTime ->
                stringUtil.getTimeString(deferredTime)
            },
            address = orderWithAmounts.address,
            comment = orderWithAmounts.comment,
            deliveryCost = stringUtil.getCostString(orderWithAmounts.deliveryCost),
            orderProductList = orderWithAmounts.orderProductList.map { orderProduct ->
                OrderProductItem(
                    uuid = orderProduct.uuid,
                    name = orderProduct.product.name,
                    newPrice = stringUtil.getCostString(orderProduct.product.newPrice),
                    oldPrice = stringUtil.getCostString(orderProduct.product.oldPrice),
                    newCost = stringUtil.getCostString(orderProduct.newCost),
                    oldCost = stringUtil.getCostString(orderProduct.oldCost),
                    photoLink = orderProduct.product.photoLink,
                    count = stringUtil.getCountString(orderProduct.count),
                )
            },
            isDelivery = orderWithAmounts.isDelivery,
            oldAmountToPay = stringUtil.getCostString(orderWithAmounts.oldAmountToPay),
            newAmountToPay = stringUtil.getCostString(orderWithAmounts.newAmountToPay),
        )
    }
}
