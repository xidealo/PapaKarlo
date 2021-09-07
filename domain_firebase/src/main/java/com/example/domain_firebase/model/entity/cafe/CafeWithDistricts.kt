package com.example.domain_firebase.model.entity.cafe

import androidx.room.Embedded
import androidx.room.Relation
import com.example.domain_firebase.model.entity.address.DistrictEntity

data class CafeWithDistricts(
    @Embedded
    val cafeEntity: CafeEntity,

    @Relation(parentColumn = "uuid", entityColumn = "cafeUuid", entity = DistrictEntity::class)
    val districtWithStreetsList: List<DistrictWithStreets>
)