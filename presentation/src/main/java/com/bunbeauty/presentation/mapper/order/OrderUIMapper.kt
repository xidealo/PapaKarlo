package com.bunbeauty.presentation.mapper.order

import com.bunbeauty.domain.model.order.LightOrder
import com.bunbeauty.domain.model.order.OrderDetails
import com.bunbeauty.presentation.item.OrderItem
import com.bunbeauty.presentation.item.OrderProductItem
import com.bunbeauty.presentation.model.OrderUI
import com.bunbeauty.presentation.util.color.IColorUtil
import com.bunbeauty.presentation.util.string.IStringUtil
import javax.inject.Inject

class OrderUIMapper @Inject constructor(
    private val stringUtil: IStringUtil,
    private val colorUtil: IColorUtil,
) : IOrderUIMapper {

    override fun toItem(order: LightOrder): OrderItem {
        return OrderItem(
            uuid = order.uuid,
            orderStatus = stringUtil.getOrderStatusString(order.status),
            orderColorResource = colorUtil.getOrderStatusColor(order.status),
            code = order.code,
            dateTime = order.dateTime
        )
    }

    override fun toUI(orderDetails: OrderDetails): OrderUI {
        return OrderUI(
            code = orderDetails.code,
            stepCount = orderDetails.stepCount,
            status = stringUtil.getOrderStatusString(orderDetails.status),
            orderStatusBackground = colorUtil.getOrderStatusColor(orderDetails.status),
            dateTime = orderDetails.dateTime,
            pickupMethod = stringUtil.getPickupMethodString(orderDetails.isDelivery),
            deferredTime = orderDetails.deferredTime,
            address = orderDetails.address,
            comment = orderDetails.comment,
            deliveryCost = stringUtil.getCostString(orderDetails.deliveryCost),
            oldTotalCost = stringUtil.getCostString(orderDetails.oldTotalCost),
            newTotalCost = stringUtil.getCostString(orderDetails.newTotalCost),
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
}