package com.bunbeauty.core.domain.order

import com.bunbeauty.core.model.order.LightOrder
import com.bunbeauty.core.model.order.OrderStatus

internal fun LightOrder.takeIfInProgress(): LightOrder? =
    takeIf { order ->
        order.status.isInProgress()
    }

internal fun OrderStatus.isInProgress(): Boolean =
    when (this) {
        OrderStatus.DONE,
        OrderStatus.CANCELED,
        -> false

        else -> true
    }
