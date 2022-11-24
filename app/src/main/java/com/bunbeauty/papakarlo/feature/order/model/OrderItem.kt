package com.bunbeauty.papakarlo.feature.order.model

import com.bunbeauty.shared.domain.model.order.OrderStatus

data class OrderItem(
    val uuid: String,
    val status: OrderStatus,
    val statusName: String,
    val code: String,
    val dateTime: String
)
