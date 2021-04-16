package com.bunbeauty.data.model.user

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class User(
    @PrimaryKey
    var userId: String = "",
    var token: String = "",
    var phone: String = "",
    var email: String = "",
    var bonus: Int = 0
) : Parcelable
