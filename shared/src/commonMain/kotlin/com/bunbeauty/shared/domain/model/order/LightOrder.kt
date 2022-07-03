package com.bunbeauty.shared.domain.model.order

import com.bunbeauty.shared.domain.model.date_time.DateTime

data class LightOrder(
    val uuid: String,
    val status: OrderStatus,
    val code: String,
    val dateTime: DateTime
)
