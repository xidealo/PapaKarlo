package com.bunbeauty.order.ui.model

import androidx.compose.ui.graphics.Color
import com.bunbeauty.core.model.order.OrderStatus

data class OrderItem(
    val uuid: String,
    val status: OrderStatus,
    val statusName: String,
    val code: String,
    val dateTime: String,
    val background: Color,
)
