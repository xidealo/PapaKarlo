package com.bunbeauty.domain.model.firebase.address

class DistrictFirebase(
    val districtEntity: DistrictEntityFirebase = DistrictEntityFirebase(),
    val streets: List<StreetFirebase> = emptyList()
)