package com.bunbeauty.data.model

import android.os.Parcelable
import androidx.room.*
import com.bunbeauty.data.model.cafe.CafeEntity
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
data class Address(
    override var uuid: String = "",
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @Embedded(prefix = "street_")
    var street: Street? = Street(),
    var house: String = "",
    var flat: String = "",
    var entrance: String = "",
    var intercom: String = "",
    var floor: String = "",
    var cafeId: String? = null
) : BaseDiffUtilModel, Parcelable
