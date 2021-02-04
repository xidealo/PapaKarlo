package com.bunbeauty.papakarlo.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Street(
    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var districtId: String? = null
) : Parcelable
