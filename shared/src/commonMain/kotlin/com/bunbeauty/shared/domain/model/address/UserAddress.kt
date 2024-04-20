package com.bunbeauty.shared.domain.model.address

data class UserAddress(
    val uuid: String,
    val street: String,
    val house: String,
    val flat: String?,
    val entrance: String?,
    val floor: String?,
    val comment: String?,
    val userUuid: String,
)
