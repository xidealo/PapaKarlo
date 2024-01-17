package com.bunbeauty.shared.data.mapper.orderaddition

import com.bunbeauty.shared.data.network.model.order.get.OrderAdditionServer
import com.bunbeauty.shared.db.OrderAdditionEntity
import com.bunbeauty.shared.domain.model.addition.OrderAddition

val mapOrderAdditionServerToOrderAdditionEntity: OrderAdditionServer.(String) -> OrderAdditionEntity =
    { orderProductUuid ->
        OrderAdditionEntity(
            uuid = uuid,
            name = name,
            orderProductUuid = orderProductUuid,
            priority = priority
        )
    }

val mapOrderAdditionServerToOrderAddition: OrderAdditionServer.() -> OrderAddition =
    {
        OrderAddition(
            uuid = uuid,
            name = name,
            priority = priority
        )
    }
