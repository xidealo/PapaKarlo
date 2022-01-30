package com.bunbeauty.data.database.entity.product

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartProductCount(
    @PrimaryKey
    val uuid: String,
    val count: Int
)
