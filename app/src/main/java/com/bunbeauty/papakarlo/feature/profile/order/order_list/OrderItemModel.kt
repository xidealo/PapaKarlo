package com.bunbeauty.papakarlo.feature.profile.order.order_list

import com.bunbeauty.domain.enums.OrderStatus

data class OrderItemModel(
    val uuid: String,
    val status: OrderStatus,
    val statusName: String,
    val statusColorId: Int,
    val code: String,
    val dateTime: String
)