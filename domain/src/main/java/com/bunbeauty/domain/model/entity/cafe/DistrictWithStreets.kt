package com.bunbeauty.domain.model.entity.cafe

import androidx.room.Embedded
import androidx.room.Relation
import com.bunbeauty.domain.model.entity.address.DistrictEntity
import com.bunbeauty.domain.model.entity.address.StreetEntity

data class DistrictWithStreets(
    @Embedded
    val district: DistrictEntity,

    @Relation(parentColumn = "uuid", entityColumn = "districtUuid")
    val streets: List<StreetEntity>
)
