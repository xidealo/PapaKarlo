package com.bunbeauty.domain.model.entity.cafe

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CafeEntity(
    @PrimaryKey
    val uuid: String,

    val fromTime: String,
    val toTime: String,
    val phone: String,
    val city: String,
    val street: String,
    val house: String,
    val comment: String?,

    val latitude: Double,
    val longitude: Double,

    val visible: Boolean,
)