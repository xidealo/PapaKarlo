package com.example.data_api.mapper

import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.Order
import com.example.domain_api.mapper.IOrderMapper
import com.example.domain_api.model.entity.user.OrderEntity
import com.example.domain_api.model.server.OrderServer
import javax.inject.Inject

class OrderMapper @Inject constructor() : IOrderMapper {

    override fun toEntityModel(order: OrderServer): OrderEntity {
        return OrderEntity(
            uuid = order.uuid,
            status = OrderStatus.valueOf(order.status),
            isDelivery = order.isDelivery,
            time = order.time,
            code = order.code,
            address = order.address,
            userUuid = order.userUuid,
        )
    }

    override fun toModel(order: OrderEntity): Order {
        return Order(
            uuid = order.uuid,
            isDelivery = order.isDelivery,
            userUuid = order.userUuid,
            phone = "",
            address = order.address,
            comment = "",
            deferredTime = null,
            time = order.time,
            code = order.code,
            orderStatus = order.status,
            orderProductList = emptyList(),
            cafeUuid = "",
        )
    }
}