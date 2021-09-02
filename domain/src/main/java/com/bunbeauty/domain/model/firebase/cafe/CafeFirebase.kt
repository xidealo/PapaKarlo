package com.bunbeauty.domain.model.firebase.cafe

import com.bunbeauty.domain.model.firebase.address.DistrictFirebase

data class CafeFirebase(
    val uuid : String = "",
    val address: CafeAddressFirebase = CafeAddressFirebase(),
    val cafeEntity: CafeEntityFirebase = CafeEntityFirebase(),
    val districts: List<DistrictFirebase> = emptyList()
)