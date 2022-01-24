package com.example.domain_api.model.entity.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEmailUpdate(
    @PrimaryKey
    val uuid: String,
    val email: String?
)
