package com.bunbeauty.papakarlo.feature.profile

import com.bunbeauty.papakarlo.feature.profile.order.order_list.OrderItemModel

data class ProfileUI(
    val userUuid: String,
    val hasAddresses: Boolean,
    val lastOrderItem: OrderItemModel?
)
