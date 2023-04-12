package com.bunbeauty.shared.domain.model.address

data class CreatedUserAddress(
    val house: String,
    val flat: String,
    val entrance: String,
    val floor: String,
    val comment: String,
    val streetUuid: String,
    val isVisible: Boolean,
)
