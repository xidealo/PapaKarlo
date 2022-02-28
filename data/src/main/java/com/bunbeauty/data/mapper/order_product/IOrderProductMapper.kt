package com.bunbeauty.data.mapper.order_product

import com.bunbeauty.data.database.entity.user.order.OrderProductEntity
import com.bunbeauty.data.network.model.order.get.OrderProductServer
import com.bunbeauty.data.network.model.order.post.OrderProductPostServer
import com.bunbeauty.domain.model.product.CartProduct
import com.bunbeauty.domain.model.product.CreatedOrderProduct
import com.bunbeauty.domain.model.product.OrderProduct
import database.OrderWithProductEntity

interface IOrderProductMapper {

    fun toEntityModel(orderProduct: OrderProductServer): OrderProductEntity
    fun toOrderProduct(orderProduct: OrderProductEntity): OrderProduct
    fun toOrderProduct(orderWithProductEntity: OrderWithProductEntity): OrderProduct
    fun toOrderProduct(orderProduct: OrderProductServer): OrderProduct
    fun toPostServerModel(orderProduct: OrderProduct): OrderProductPostServer
    fun toPostServerModel(cartProduct: CartProduct): OrderProductPostServer
    fun toPostServerModel(createdOrderProduct: CreatedOrderProduct): OrderProductPostServer
}