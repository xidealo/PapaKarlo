package com.example.domain_api.mapper

import com.bunbeauty.domain.model.Order
import com.example.domain_api.model.entity.user.order.OrderWithProducts
import com.example.domain_api.model.server.order.get.OrderServer
import com.example.domain_api.model.server.order.post.OrderPostServer

interface IOrderMapper {

    fun toEntityModel(order: OrderServer): OrderWithProducts
    fun toModel(order: OrderWithProducts): Order
    fun toPostServerModel(order: Order): OrderPostServer
}