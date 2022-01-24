package com.example.domain_api.mapper

import com.bunbeauty.domain.model.order.CreatedOrder
import com.bunbeauty.domain.model.order.Order
import com.example.domain_api.model.entity.user.order.OrderStatusUpdate
import com.example.domain_api.model.entity.user.order.OrderWithProducts
import com.example.domain_api.model.server.order.get.OrderServer
import com.example.domain_api.model.server.order.post.OrderPostServer

interface IOrderMapper {

    fun toEntityModel(order: OrderServer): OrderWithProducts
    fun toOrderStatusUpdate(order: Order): OrderStatusUpdate
    fun toModel(order: OrderWithProducts): Order
    fun toModel(order: OrderServer): Order
    fun toPostServerModel(createdOrder: CreatedOrder): OrderPostServer
}