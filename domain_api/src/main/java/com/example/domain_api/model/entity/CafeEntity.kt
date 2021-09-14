package com.example.domain_api.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class CafeEntity(
    @PrimaryKey
    val uuid: String,

    val address: String,

    val fromTime: String,

    val toTime: String,

    val phone: String,

    val latitude: Double,

    val longitude: Double,

    val visible: Boolean,

    val cityUuid: String,
)