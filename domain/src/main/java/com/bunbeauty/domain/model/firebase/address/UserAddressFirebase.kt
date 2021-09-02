package com.bunbeauty.domain.model.firebase.address

data class UserAddressFirebase(
    val street: StreetFirebase = StreetFirebase(),
    val house: String = "",
    val flat: String? = null,
    val entrance: String? = null,
    val floor: String? = null,
    val comment: String? = null,
)