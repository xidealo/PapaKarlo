package com.example.domain_firebase.model.firebase.cafe

import com.example.domain_firebase.model.firebase.address.DistrictFirebase

data class CafeFirebase(
    val uuid : String = "",
    val address: CafeAddressFirebase = CafeAddressFirebase(),
    val cafeEntity: CafeEntityFirebase = CafeEntityFirebase(),
    val districts: List<DistrictFirebase> = emptyList()
)