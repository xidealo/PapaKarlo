package com.bunbeauty.data.model

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
        parentColumns = ["id"],
        childColumns = ["districtId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("districtId")]
)
data class Street(
    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var districtId: String? = null
) : Parcelable
