package com.example.domain_api.model.entity.cafe

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class CafeEntity(
    @PrimaryKey
    val uuid: String,
    val fromTime: Int,
    val toTime: Int,
    val offset: Int,
    val phone: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val cityUuid: String,
    val isVisible: Boolean,
)