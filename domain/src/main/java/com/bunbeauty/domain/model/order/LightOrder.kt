package com.bunbeauty.domain.model.order

import com.bunbeauty.domain.enums.OrderStatus

data class LightOrder(
    val uuid: String,
    val status: OrderStatus,
    val code: String,
    val dateTime: String
)
