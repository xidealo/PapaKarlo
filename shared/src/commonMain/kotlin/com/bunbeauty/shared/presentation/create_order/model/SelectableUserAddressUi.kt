package com.bunbeauty.shared.presentation.create_order.model

class SelectableUserAddressUi(
    val uuid: String,
    val street: String,
    val house: String,
    val flat: String?,
    val entrance: String?,
    val floor: String?,
    val comment: String?,
    val isSelected: Boolean,
)