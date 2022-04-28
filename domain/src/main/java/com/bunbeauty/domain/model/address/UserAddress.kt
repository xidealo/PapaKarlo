package com.bunbeauty.domain.model.address

import com.bunbeauty.domain.model.Street

data class UserAddress(
    val uuid: String,
    val street: Street,
    val house: String,
    val flat: String?,
    val entrance: String?,
    val floor: String?,
    val comment: String?,
    val userUuid: String,
)
