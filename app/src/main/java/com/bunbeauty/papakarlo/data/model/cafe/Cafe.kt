package com.bunbeauty.papakarlo.data.model.cafe

import androidx.room.Embedded
import androidx.room.Relation
import com.bunbeauty.papakarlo.data.model.Address
import com.bunbeauty.papakarlo.data.model.District
import com.bunbeauty.papakarlo.data.model.DistrictEntity

data class Cafe(
    @Embedded
    val cafeEntity: CafeEntity = CafeEntity(),

    @Relation(parentColumn = "id", entityColumn = "cafeId", entity = DistrictEntity::class)
    val districts: List<District> = arrayListOf(),

    @Relation(parentColumn = "id", entityColumn = "cafeId")
    val address: Address = Address(),
) {
    companion object {
        const val CAFES = "cafes"
    }
}
