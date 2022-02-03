package com.bunbeauty.data.mapper.order

import com.bunbeauty.data.database.entity.user.order.OrderEntityWithProducts
import com.bunbeauty.data.database.entity.user.order.OrderStatusUpdate
import com.bunbeauty.data.network.model.order.get.OrderServer
import com.bunbeauty.data.network.model.order.post.OrderPostServer
import com.bunbeauty.domain.model.order.CreatedOrder
import com.bunbeauty.domain.model.order.LightOrder
import com.bunbeauty.domain.model.order.Order
import com.bunbeauty.domain.model.order.OrderCode

interface IOrderMapper {

    fun toOrderEntityWithProducts(orderServer: OrderServer): OrderEntityWithProducts
    fun toLightOrder(orderEntityWithProducts: OrderEntityWithProducts): LightOrder
    fun toOrderStatusUpdate(orderServer: OrderServer): OrderStatusUpdate
    fun toOrderCode(orderServer: OrderServer): OrderCode
    fun toOrder(orderEntityWithProducts: OrderEntityWithProducts): Order
    fun toOrder(orderServer: OrderServer): Order
    fun toOrderPostServer(createdOrder: CreatedOrder): OrderPostServer
}