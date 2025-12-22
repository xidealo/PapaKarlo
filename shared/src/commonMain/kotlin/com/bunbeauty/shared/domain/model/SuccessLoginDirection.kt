package com.bunbeauty.shared.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class SuccessLoginDirection {
    BACK_TO_PROFILE,
    TO_CREATE_ORDER,
}
