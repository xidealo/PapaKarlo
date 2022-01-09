package com.example.domain_api.mapper

import com.bunbeauty.domain.model.product.CartProduct
import com.bunbeauty.domain.model.product.CreatedOrderProduct
import com.bunbeauty.domain.model.product.OrderProduct
import com.example.domain_api.model.entity.user.order.OrderProductEntity
import com.example.domain_api.model.server.order.get.OrderProductServer
import com.example.domain_api.model.server.order.post.OrderProductPostServer

interface IOrderProductMapper {

    fun toEntityModel(orderProduct: OrderProductServer): OrderProductEntity
    fun toModel(orderProduct: OrderProductEntity): OrderProduct
    fun toModel(orderProduct: OrderProductServer): OrderProduct
    fun toPostServerModel(orderProduct: OrderProduct): OrderProductPostServer
    fun toPostServerModel(cartProduct: CartProduct): OrderProductPostServer
    fun toPostServerModel(createdOrderProduct: CreatedOrderProduct): OrderProductPostServer
}