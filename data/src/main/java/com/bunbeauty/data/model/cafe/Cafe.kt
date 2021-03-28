package com.bunbeauty.data.model.cafe

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import com.bunbeauty.data.model.Address
import com.bunbeauty.data.model.District
import com.bunbeauty.data.model.DistrictEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cafe(
    @Embedded
    val cafeEntity: CafeEntity = CafeEntity(),

    @Relation(parentColumn = "id", entityColumn = "cafeId", entity = DistrictEntity::class)
    val districts: List<District> = arrayListOf(),

    @Relation(parentColumn = "id", entityColumn = "cafeId")
    val address: Address? = Address(),
) : Parcelable {
    companion object {
        const val CAFES = "cafes"
    }
}
