package com.example.domain_api.model.entity

import androidx.room.Entity

@Entity
data class ProfileEntity(
    val uuid: String,
    val phone: String,
    val email: String
)
