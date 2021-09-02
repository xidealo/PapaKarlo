package com.bunbeauty.domain.model.entity.address

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    foreignKeys = [ForeignKey(
        entity = DistrictEntity::class,
        parentColumns = ["uuid"],
        childColumns = ["districtUuid"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class StreetEntity(
    @PrimaryKey
    var uuid: String,
    var name: String,
    var districtUuid: String
) : Parcelable
