package com.bunbeauty.shared.domain.model.cafe

data class SelectableCafe(
    val cafe: Cafe,
    val isSelected: Boolean,
    val canBePickup: Boolean,
)
