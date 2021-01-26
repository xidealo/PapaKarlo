package com.bunbeauty.papakarlo.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Address(
    @PrimaryKey(autoGenerate = true)
    override var id: Long = 0,
    var street: String = "",
    var house: String = "",
    var flat: String = "",
    var entrance: String = "",
    var intercom: String = "",
    var floor: String = "",
    var city: String = ""
) : BaseModel(), Parcelable
