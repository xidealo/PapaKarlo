package com.bun_beauty.papakarlo.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Product(
    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var cost: Long = 0,
    var gram: Int = 0,
    var description: String = "",
    var photoLink: String = ""
):Parcelable {
}