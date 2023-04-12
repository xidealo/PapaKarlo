package com.bunbeauty.shared.domain.model.address

import com.bunbeauty.shared.domain.model.street.Street

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
