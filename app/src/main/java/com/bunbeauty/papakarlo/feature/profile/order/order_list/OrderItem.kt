package com.bunbeauty.papakarlo.feature.profile.order.order_list

import com.bunbeauty.domain.model.order.OrderStatus

data class OrderItem(
    val uuid: String,
    val status: OrderStatus,
    val statusName: String,
    val code: String,
    val dateTime: String
)