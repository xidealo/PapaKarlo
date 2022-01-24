package com.example.domain_firebase.model.firebase.cafe

import com.example.domain_firebase.model.firebase.address.StreetFirebase

class CafeAddressFirebase(
    val city: String = "",
    val street: StreetFirebase = StreetFirebase(),
    val house: String = "",
    val comment: String? = null
)