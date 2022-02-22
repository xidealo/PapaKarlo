package com.bunbeauty.papakarlo.feature.profile

import com.bunbeauty.papakarlo.feature.profile.order.order_list.OrderItem

data class ProfileUI(
    val userUuid: String,
    val hasAddresses: Boolean,
    val lastOrderItem: OrderItem?
)
