package com.bunbeauty.papakarlo.data.model.cafe

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Embedded
import androidx.room.Relation
import com.bunbeauty.papakarlo.data.model.Address

data class Cafe(

    @Embedded
    val cafeEntity: CafeEntity = CafeEntity(),

    @Relation(parentColumn = "addressId", entityColumn = "id")
    val address: Address = Address()
) {

    companion object {
        const val CAFES = "cafes"
    }
}
