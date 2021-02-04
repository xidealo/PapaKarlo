package com.bunbeauty.papakarlo.data.model.cafe

import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Relation
import com.bunbeauty.papakarlo.data.model.Address
import com.bunbeauty.papakarlo.data.model.District
import com.bunbeauty.papakarlo.data.model.Street

data class Cafe(
    @Embedded
    val cafeEntity: CafeEntity = CafeEntity(),

    @Relation(parentColumn = "addressId", entityColumn = "id")
    val address: Address = Address(),

    @Relation(parentColumn = "id", entityColumn = "cafeId")
    val districts: List<District> = arrayListOf(),
) {
    companion object {
        const val CAFES = "cafes"
    }
}
