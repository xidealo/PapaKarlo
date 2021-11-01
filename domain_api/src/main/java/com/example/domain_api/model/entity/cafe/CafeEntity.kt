package com.example.domain_api.model.entity.cafe

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
    val cityUuid: String,
    val visible: Boolean,
)