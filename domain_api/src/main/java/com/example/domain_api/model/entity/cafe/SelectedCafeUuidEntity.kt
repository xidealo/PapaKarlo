package com.example.domain_api.model.entity.cafe

import androidx.room.Entity

@Entity(primaryKeys = ["userUuid", "cityUuid"])
data class SelectedCafeUuidEntity(
    val userUuid: String,
    val cityUuid: String,
    val cafeUuid: String,
)