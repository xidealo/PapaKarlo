package com.bunbeauty.shared.data.mapper.order

import com.bunbeauty.shared.data.network.model.order.get.OrderServer
import com.bunbeauty.shared.data.network.model.order.post.OrderPostServer
import com.bunbeauty.shared.db.OrderEntity
import com.bunbeauty.shared.db.OrderWithProductEntity
import com.bunbeauty.shared.domain.model.order.CreatedOrder
import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.domain.model.order.Order
import com.bunbeauty.shared.domain.model.order.OrderCode

interface IOrderMapper {

    fun toOrderWithProductEntityList(orderServer: OrderServer): List<OrderWithProductEntity>
    fun toLightOrder(orderEntity: OrderEntity): LightOrder
    fun toLightOrder(orderServer: OrderServer): LightOrder
    fun toOrderCode(orderServer: OrderServer): OrderCode
    fun toOrder(orderWithProductEntityList: List<OrderWithProductEntity>): Order?
    fun toOrder(orderServer: OrderServer): Order
    fun toOrderPostServer(createdOrder: CreatedOrder): OrderPostServer
}