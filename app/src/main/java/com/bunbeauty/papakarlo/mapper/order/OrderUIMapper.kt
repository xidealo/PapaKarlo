package com.bunbeauty.papakarlo.mapper.order

import com.bunbeauty.domain.model.order.LightOrder
import com.bunbeauty.domain.model.order.OrderWithAmounts
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.feature.profile.order.order_details.OrderProductItemModel
import com.bunbeauty.papakarlo.feature.profile.order.order_details.OrderUI
import com.bunbeauty.papakarlo.feature.profile.order.order_list.OrderItemModel
import com.bunbeauty.papakarlo.util.color.IColorUtil
import com.bunbeauty.papakarlo.util.string.IStringUtil

class OrderUIMapper(
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
                OrderProductItemModel(
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