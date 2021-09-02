package com.bunbeauty.domain.model.ui

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import com.bunbeauty.domain.model.entity.address.DistrictEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class District(
    var uuid: String,
    var name: String,
    var cafeUuid: String,



    @Embedded
    val districtEntity: DistrictEntity,

    @Relation(parentColumn = "id", entityColumn = "districtId")
    val streets: List<Street> = arrayListOf()
): Parcelable