package com.bunbeauty.shared.data.mapper.order_addition

import com.bunbeauty.shared.data.network.model.order.get.OrderAdditionServer
import com.bunbeauty.shared.db.OrderAdditionEntity

val mapOrderAdditionServerToOrderAdditionEntity: OrderAdditionServer.(String) -> OrderAdditionEntity =
    { orderProductUuid ->
        OrderAdditionEntity(
            uuid = uuid,
            name = name,
            orderProductUuid = orderProductUuid,
        )
    }