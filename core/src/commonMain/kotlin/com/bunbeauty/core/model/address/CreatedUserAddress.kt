package com.bunbeauty.core.model.address

import com.bunbeauty.core.model.Suggestion

data class CreatedUserAddress(
    val street: Suggestion,
    val house: String,
    val flat: String,
    val entrance: String,
    val floor: String,
    val comment: String,
    val isVisible: Boolean,
    val cityUuid: String,
)
