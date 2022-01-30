package com.bunbeauty.data.mapper.order

import com.bunbeauty.data.database.entity.user.order.OrderStatusUpdate
import com.bunbeauty.data.database.entity.user.order.OrderWithProducts
import com.bunbeauty.data.network.model.order.get.OrderServer
import com.bunbeauty.data.network.model.order.post.OrderPostServer
import com.bunbeauty.domain.model.order.CreatedOrder
import com.bunbeauty.domain.model.order.LightOrder
import com.bunbeauty.domain.model.order.Order

interface IOrderMapper {

    fun toEntityModel(order: OrderServer): OrderWithProducts
    fun toLightOrder(order: Order): LightOrder
    fun toLightOrder(order: OrderWithProducts): LightOrder
    fun toOrderStatusUpdate(order: Order): OrderStatusUpdate
    fun toModel(order: OrderWithProducts): Order
    fun toModel(order: OrderServer): Order
    fun toPostServerModel(createdOrder: CreatedOrder): OrderPostServer
}