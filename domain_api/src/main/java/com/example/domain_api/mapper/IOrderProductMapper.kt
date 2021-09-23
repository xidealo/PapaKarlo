package com.example.domain_api.mapper

import com.bunbeauty.domain.model.product.OrderProduct
import com.example.domain_api.model.entity.user.order.OrderProductEntity
import com.example.domain_api.model.server.order.OrderProductPostServer
import com.example.domain_api.model.server.order.OrderProductServer

interface IOrderProductMapper {

    fun toEntityModel(orderProduct: OrderProductServer): OrderProductEntity
    fun toModel(orderProduct: OrderProductEntity): OrderProduct
    fun toPostServerModel(orderProduct: OrderProduct): OrderProductPostServer
}