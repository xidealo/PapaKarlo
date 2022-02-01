package com.bunbeauty.domain.model.profile

import com.bunbeauty.domain.model.order.LightOrder

data class Profile(
    val userUuid: String,
    val hasAddresses: Boolean,
    val lastOrder: LightOrder?
)
