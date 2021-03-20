package com.bunbeauty.data.model.cafe

import androidx.room.Embedded
import androidx.room.Relation
import com.bunbeauty.data.model.Address
import com.bunbeauty.data.model.District
import com.bunbeauty.data.model.DistrictEntity

data class Cafe(
    @Embedded
    val cafeEntity: CafeEntity = CafeEntity(),

    @Relation(parentColumn = "id", entityColumn = "cafeId", entity = DistrictEntity::class)
    val districts: List<District> = arrayListOf(),

    @Relation(parentColumn = "id", entityColumn = "cafeId")
    val address: Address? = Address(),
) {
    companion object {
        const val CAFES = "cafes"
    }
}
