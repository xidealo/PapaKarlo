package com.bunbeauty.shared.domain.feature.order

import com.bunbeauty.core.model.order.LightOrder
import com.bunbeauty.core.model.order.Order

class LightOrderMapper {
    fun toLightOrder(order: Order): LightOrder =
        LightOrder(
            uuid = order.uuid,
            status = order.status,
            code = order.code,
            dateTime = order.dateTime,
        )
}
