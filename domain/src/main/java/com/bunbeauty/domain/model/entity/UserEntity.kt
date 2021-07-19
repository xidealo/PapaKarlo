package com.bunbeauty.domain.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class UserEntity(
    @PrimaryKey
    var uuid: String = "",
    var phone: String = "",
    var email: String = "",
    @Ignore
    var bonusList: MutableList<Int> = arrayListOf()
) : Parcelable
