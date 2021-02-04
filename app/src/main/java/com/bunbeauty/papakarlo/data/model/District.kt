package com.bunbeauty.papakarlo.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class District(
    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var cafeId: String? = null,
    @Ignore
    val streets: List<Street> = arrayListOf()
): Parcelable
