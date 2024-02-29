package com.bunbeauty.shared.domain.model.address

data class SelectableUserAddress(
    val uuid: String,
    val street: String,
    val house: String,
    val flat: String?,
    val entrance: String?,
    val floor: String?,
    val comment: String?,
    val userUuid: String,
    val isSelected:Boolean
)
