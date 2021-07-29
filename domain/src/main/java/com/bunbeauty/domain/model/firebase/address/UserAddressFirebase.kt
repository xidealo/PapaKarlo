package com.bunbeauty.domain.model.firebase.address

data class UserAddressFirebase(
    val street: String = "",
    val house: String = "",
    val flat: String = "",
    val entrance: String = "",
    val floor: String = "",
    val comment: String = "",
)