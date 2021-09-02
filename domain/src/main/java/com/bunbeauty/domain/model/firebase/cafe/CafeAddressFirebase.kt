package com.bunbeauty.domain.model.firebase.cafe

import com.bunbeauty.domain.model.firebase.address.StreetFirebase

class CafeAddressFirebase(
    val city: String = "",
    val street: StreetFirebase = StreetFirebase(),
    val house: String = "",
    val comment: String? = null
)