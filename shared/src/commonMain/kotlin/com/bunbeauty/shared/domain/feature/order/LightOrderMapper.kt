package com.bunbeauty.shared.domain.feature.order

import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.domain.model.order.Order

class LightOrderMapper {
    fun toLightOrder(order: Order): LightOrder {
        return LightOrder(
            uuid = order.uuid,
            status = order.status,
            code = order.code,
            dateTime = order.dateTime
        )
    }
}