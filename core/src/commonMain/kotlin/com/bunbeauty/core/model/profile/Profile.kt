package com.bunbeauty.core.model.profile

import com.bunbeauty.core.model.order.LightOrder

sealed class Profile {
    data class Authorized(
        val userUuid: String,
        val hasAddresses: Boolean,
        val lastOrder: LightOrder?,
    ) : Profile()

    object Unauthorized : Profile()
}
