package com.example.domain_api.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StreetEntity(
    @PrimaryKey
    val uuid: String,
    val name: String,
    val cityUuid: String,
)
