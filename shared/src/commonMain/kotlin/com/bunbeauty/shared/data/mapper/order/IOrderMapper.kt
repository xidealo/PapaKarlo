package com.bunbeauty.shared.data.mapper.order

import com.bunbeauty.shared.data.network.model.order.get.LightOrderServer
import com.bunbeauty.shared.data.network.model.order.get.OrderServer
import com.bunbeauty.shared.data.network.model.order.post.OrderPostServer
import com.bunbeauty.shared.db.LightOrderEntity
import com.bunbeauty.shared.db.OrderEntity
import com.bunbeauty.shared.db.OrderWithProductEntity
import com.bunbeauty.shared.domain.model.order.CreatedOrder
import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.domain.model.order.Order
import com.bunbeauty.shared.domain.model.order.OrderCode

interface IOrderMapper {

    fun toLightOrder(orderEntity: OrderEntity): LightOrder
    fun toLightOrder(orderServer: OrderServer): LightOrder
    fun toLightOrder(lightOrderServer: LightOrderServer): LightOrder
    fun toLightOrder(lightOrderEntity: LightOrderEntity): LightOrder
    fun toLightOrderEntity(lightOrderServer: LightOrderServer): LightOrderEntity
    fun toOrderCode(orderServer: OrderServer): OrderCode
    fun toOrder(orderWithProductEntityList: List<OrderWithProductEntity>): Order?
    fun toOrder(orderServer: OrderServer): Order
    fun toOrderEntity(orderServer: OrderServer): OrderEntity
    fun toOrderPostServer(createdOrder: CreatedOrder): OrderPostServer
}
