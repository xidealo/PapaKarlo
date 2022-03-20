package com.bunbeauty.domain.model.order

import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.date_time.DateTime

data class LightOrder(
    val uuid: String,
    val status: OrderStatus,
    val code: String,
    val dateTime: DateTime
)
