package com.bunbeauty.shared.domain.model.address

import com.bunbeauty.shared.domain.model.Suggestion

data class CreatedUserAddress(
    val street: Suggestion,
    val house: String,
    val flat: String,
    val entrance: String,
    val floor: String,
    val comment: String,
    val isVisible: Boolean,
    val cityUuid: String
)
