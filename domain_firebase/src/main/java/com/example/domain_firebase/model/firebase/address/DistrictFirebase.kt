package com.example.domain_firebase.model.firebase.address

class DistrictFirebase(
    val districtEntity: DistrictEntityFirebase = DistrictEntityFirebase(),
    val streets: List<StreetFirebase> = emptyList()
)