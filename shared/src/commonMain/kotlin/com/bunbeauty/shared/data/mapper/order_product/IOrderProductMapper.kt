package com.bunbeauty.shared.data.mapper.order_product

import com.bunbeauty.core.model.product.CreatedOrderProduct
import com.bunbeauty.core.model.product.OrderProduct
import com.bunbeauty.shared.data.network.model.order.get.OrderProductServer
import com.bunbeauty.shared.data.network.model.order.post.OrderProductPostServer
import com.bunbeauty.shared.db.OrderWithProductEntity

interface IOrderProductMapper {
    fun toOrderProduct(orderWithProductEntityList: List<OrderWithProductEntity>): List<OrderProduct>

    fun toOrderProduct(orderProduct: OrderProductServer): OrderProduct

    fun toPostServerModel(createdOrderProduct: CreatedOrderProduct): OrderProductPostServer
}
