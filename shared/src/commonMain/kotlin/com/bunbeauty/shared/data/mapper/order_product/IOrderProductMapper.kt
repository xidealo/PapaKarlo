package com.bunbeauty.shared.data.mapper.order_product

import com.bunbeauty.shared.data.network.model.order.get.OrderProductServer
import com.bunbeauty.shared.data.network.model.order.post.OrderProductPostServer
import com.bunbeauty.shared.db.OrderWithProductEntity
import com.bunbeauty.shared.domain.model.product.CreatedOrderProduct
import com.bunbeauty.shared.domain.model.product.OrderProduct

interface IOrderProductMapper {

    fun toOrderProduct(orderWithProductEntity: List<OrderWithProductEntity>): List<OrderProduct>
    fun toOrderProduct(orderProduct: OrderProductServer): OrderProduct
    fun toPostServerModel(createdOrderProduct: CreatedOrderProduct): OrderProductPostServer
}