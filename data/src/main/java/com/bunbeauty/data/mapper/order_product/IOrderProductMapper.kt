package com.bunbeauty.data.mapper.order_product

import com.bunbeauty.data.network.model.order.get.OrderProductServer
import com.bunbeauty.data.network.model.order.post.OrderProductPostServer
import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.model.product.CreatedOrderProduct
import com.bunbeauty.shared.domain.model.product.OrderProduct
import database.OrderWithProductEntity

interface IOrderProductMapper {

    fun toOrderProduct(orderWithProductEntity: OrderWithProductEntity): OrderProduct
    fun toOrderProduct(orderProduct: OrderProductServer): OrderProduct
    fun toPostServerModel(orderProduct: OrderProduct): OrderProductPostServer
    fun toPostServerModel(cartProduct: CartProduct): OrderProductPostServer
    fun toPostServerModel(createdOrderProduct: CreatedOrderProduct): OrderProductPostServer
}