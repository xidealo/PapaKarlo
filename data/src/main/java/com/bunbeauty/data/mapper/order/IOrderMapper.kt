package com.bunbeauty.data.mapper.order

import com.bunbeauty.data.database.entity.user.order.OrderStatusUpdate
import com.bunbeauty.data.database.entity.user.order.OrderWithProducts
import com.bunbeauty.data.network.model.order.get.OrderServer
import com.bunbeauty.data.network.model.order.post.OrderPostServer
import com.bunbeauty.domain.model.order.CreatedOrder
import com.bunbeauty.domain.model.order.LightOrder
import com.bunbeauty.domain.model.order.OrderCode
import com.bunbeauty.domain.model.order.OrderDetails

interface IOrderMapper {

    fun toEntityModel(order: OrderServer): OrderWithProducts
    fun toLightOrder(order: OrderWithProducts): LightOrder
    fun toOrderStatusUpdate(order: OrderServer): OrderStatusUpdate
    fun toOrderCode(order: OrderServer): OrderCode
    fun toOrderDetails(order: OrderWithProducts): OrderDetails
    fun toOrderDetails(order: OrderServer): OrderDetails
    fun toPostServerModel(createdOrder: CreatedOrder): OrderPostServer
}