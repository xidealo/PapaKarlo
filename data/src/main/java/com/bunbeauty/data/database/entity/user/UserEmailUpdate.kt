package com.bunbeauty.data.database.entity.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEmailUpdate(
    @PrimaryKey
    val uuid: String,
    val email: String?
)
