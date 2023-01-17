package com.bunbeauty.shared.presentation.order_details

import com.bunbeauty.shared.domain.model.order.Order
import com.bunbeauty.shared.presentation.create_order.TimeMapper

class OrderDetailsMapper(
    private val timeMapper: TimeMapper
) {

    fun toOrderDetails(order: Order): List<OrderDetails> {
        return buildList {
            add(
                OrderInfo(
                    code = order.code,
                    status = order.status,
                    dateTime = order.dateTime,
                    deferredTime = timeMapper.toUiModel(order.deferredTime),
                    address = order.address,
                    comment = order.comment,
                    isDelivery = order.isDelivery,
                )
            )
            addAll(
                order.orderProductList.map { orderProduct ->
                    OrderProductItem(
                        uuid = orderProduct.uuid,
                        name = orderProduct.product.name,
                        newPrice = orderProduct.product.newPrice,
                        oldPrice = orderProduct.product.oldPrice,
                        newCost = orderProduct.product.newPrice * orderProduct.count,
                        oldCost = orderProduct.product.oldPrice?.let { oldPrice ->
                            oldPrice * orderProduct.count
                        },
                        photoLink = orderProduct.product.photoLink,
                        count = orderProduct.count,
                    )
                }
            )
        }
    }
}