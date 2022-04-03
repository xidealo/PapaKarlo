package com.bunbeauty.domain.model.profile

import com.bunbeauty.domain.model.order.LightOrder

sealed class Profile {

    data class Authorized(
        val userUuid: String,
        val hasAddresses: Boolean,
        val lastOrder: LightOrder?
    ) : Profile()

    object Unauthorized : Profile()

}
