package com.bunbeauty.shared.ui.screen.order.model

import com.bunbeauty.core.model.order.OrderStatus

data class OrderItem(
    val uuid: String,
    val status: OrderStatus,
    val statusName: String,
    val code: String,
    val dateTime: String,
)
