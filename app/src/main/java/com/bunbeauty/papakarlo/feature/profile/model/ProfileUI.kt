package com.bunbeauty.papakarlo.feature.profile.model

import com.bunbeauty.papakarlo.feature.order.model.OrderItem

data class ProfileUI(
    val userUuid: String,
    val hasAddresses: Boolean,
    val lastOrderItem: OrderItem?
)
