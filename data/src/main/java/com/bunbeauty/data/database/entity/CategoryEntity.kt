package com.bunbeauty.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class CategoryEntity(
    @PrimaryKey
    val uuid: String,
    val name: String,
    val priority: Int,
)