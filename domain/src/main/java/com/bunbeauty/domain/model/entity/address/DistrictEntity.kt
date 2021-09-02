package com.bunbeauty.domain.model.entity.address

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.bunbeauty.domain.model.entity.cafe.CafeEntity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    foreignKeys = [ForeignKey(
        entity = CafeEntity::class,
        parentColumns = ["uuid"],
        childColumns = ["cafeUuid"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class DistrictEntity(
    @PrimaryKey
    var uuid: String,
    var name: String,
    var cafeUuid: String
) : Parcelable