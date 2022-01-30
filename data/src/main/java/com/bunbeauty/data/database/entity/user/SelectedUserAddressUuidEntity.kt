package com.bunbeauty.data.database.entity.user

import androidx.room.Entity

@Entity(primaryKeys = ["userUuid","cityUuid"])
data class SelectedUserAddressUuidEntity(
    val userUuid: String,
    val cityUuid: String,
    val addressUuid: String,
)