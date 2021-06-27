package com.bunbeauty.domain.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

@Parcelize
data class District(
    @Embedded
    val districtEntity: DistrictEntity = DistrictEntity(),

    @Relation(parentColumn = "id", entityColumn = "districtId")
    val streets: List<Street> = arrayListOf()
): Parcelable