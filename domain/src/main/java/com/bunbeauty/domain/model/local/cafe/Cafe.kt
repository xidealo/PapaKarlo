package com.bunbeauty.domain.model.local.cafe

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import com.bunbeauty.domain.model.local.address.CafeAddress
import com.bunbeauty.domain.model.local.District
import com.bunbeauty.domain.model.local.DistrictEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cafe(
    @Embedded
    val cafeEntity: CafeEntity = CafeEntity(),

    @Relation(parentColumn = "id", entityColumn = "cafeId", entity = DistrictEntity::class)
    val districts: List<District> = arrayListOf(),

    @Relation(parentColumn = "id", entityColumn = "cafeId")
    val address: CafeAddress? = CafeAddress(),
) : Parcelable
