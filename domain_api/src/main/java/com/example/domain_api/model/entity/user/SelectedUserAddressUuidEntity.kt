package com.example.domain_api.model.entity.user

import androidx.room.Entity

@Entity(primaryKeys = ["userUuid","cityUuid"])
data class SelectedUserAddressUuidEntity(
    val userUuid: String,
    val cityUuid: String,
    val addressUuid: String,
)