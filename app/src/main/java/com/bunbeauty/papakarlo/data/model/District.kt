package com.bunbeauty.papakarlo.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class District(
    @Embedded
    val districtEntity: DistrictEntity = DistrictEntity(),

    @Relation(parentColumn = "id", entityColumn = "districtId")
    val streets: List<Street> = arrayListOf()
)