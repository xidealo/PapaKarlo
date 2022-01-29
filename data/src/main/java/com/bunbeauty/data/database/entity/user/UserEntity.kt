package com.bunbeauty.data.database.entity.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey
    var uuid: String,
    var phone: String,
    var email: String?
)
