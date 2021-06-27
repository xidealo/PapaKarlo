package com.bunbeauty.domain.model.user

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class User(
    @PrimaryKey
    var userId: String = "",
    var phone: String = "",
    var email: String = "",
    @Ignore
    var bonusList: MutableList<Int> = arrayListOf()
) : Parcelable
