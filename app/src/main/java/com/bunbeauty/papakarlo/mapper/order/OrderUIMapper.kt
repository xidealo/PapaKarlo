package com.bunbeauty.papakarlo.mapper.order

import com.bunbeauty.domain.model.order.LightOrder
import com.bunbeauty.domain.model.order.OrderWithAmounts
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.feature.profile.order.order_details.OrderProductItemModel
import com.bunbeauty.papakarlo.feature.profile.order.order_details.OrderUI
import com.bunbeauty.papakarlo.feature.profile.order.order_list.OrderItemModel
import com.bunbeauty.papakarlo.util.color.IColorUtil
import com.bunbeauty.papakarlo.util.string.IStringUtil

class OrderUIMapper constructor(
    private val stringUtil: IStringUtil,
    private val colorUtil: IColorUtil
) : IOrderUIMapper {

    override fun toItem(order: LightOrder): OrderItemModel {
        return OrderItemModel(
            uuid = order.uuid,
            status = order.status,
            statusName = stringUtil.getOrderStatusName(order.status),
            statusColorId = colorUtil.getOrderStatusColorAttr(order.status),
            code = order.code,
            dateTime = stringUtil.getDateTimeString(order.dateTime)
        )
    }

    override fun toOrderUI(orderWithAmounts: OrderWithAmounts): OrderUI {
        return OrderUI(
            code = orderWithAmounts.order.code,
            status = orderWithAmounts.order.status,
            statusName = stringUtil.getOrderStatusName(orderWithAmounts.order.status),
            dateTime = stringUtil.getDateTimeString(orderWithAmounts.order.dateTime),
            pickupMethod = stringUtil.getPickupMethodString(orderWithAmounts.order.isDelivery),
            deferredTimeHintStringId = if (orderWithAmounts.order.isDelivery) {
                R.string.msg_order_details_deferred_time_delivery
            } else {
                R.string.msg_order_details_deferred_time_pickup
            },
            deferredTime = orderWithAmounts.order.deferredTime?.let { deferredTime ->
                stringUtil.getTimeString(deferredTime)
            },
            address = orderWithAmounts.order.address,
            comment = orderWithAmounts.order.comment,
            deliveryCost = stringUtil.getCostString(orderWithAmounts.order.deliveryCost),
            orderProductList = orderWithAmounts.order.orderProductList.map { orderProduct ->
                OrderProductItemModel(
                    uuid = orderProduct.uuid,
                    name = orderProduct.product.name,
                    newCost = stringUtil.getCostString(orderProduct.product.newPrice),
                    oldCost = stringUtil.getCostString(orderProduct.product.oldPrice),
                    photoLink = orderProduct.product.photoLink,
                    count = stringUtil.getCountString(orderProduct.count),
                )
            },
            isDelivery = orderWithAmounts.order.isDelivery,
            oldAmountToPay = stringUtil.getCostString(orderWithAmounts.oldAmountToPay),
            newAmountToPay = stringUtil.getCostString(orderWithAmounts.newAmountToPay),
        )
    }
}