package com.bunbeauty.domain.model.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.bunbeauty.domain.model.local.cafe.CafeEntity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    indices = [Index("cafeId")],
    foreignKeys = [ForeignKey(
        entity = CafeEntity::class,
        parentColumns = ["id"],
        childColumns = ["cafeId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class DistrictEntity(
    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var cafeId: String? = null,
) : Parcelable