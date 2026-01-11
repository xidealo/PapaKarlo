package com.bunbeauty.core.model.order

import com.bunbeauty.core.model.date_time.DateTime

data class LightOrder(
    val uuid: String,
    val status: OrderStatus,
    val code: String,
    val dateTime: DateTime,
)
