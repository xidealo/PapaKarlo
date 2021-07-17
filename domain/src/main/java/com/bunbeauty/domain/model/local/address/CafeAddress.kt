package com.bunbeauty.domain.model.local.address

import android.os.Parcelable
import androidx.room.*
import com.bunbeauty.domain.model.local.address.Address
import com.bunbeauty.domain.model.local.cafe.CafeEntity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    indices = [Index(unique = true, value = ["cafeId"])],
    foreignKeys = [ForeignKey(
        entity = CafeEntity::class,
        parentColumns = ["id"],
        childColumns = ["cafeId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class CafeAddress(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var cafeId: String? = null,
) : Address(), Parcelable
