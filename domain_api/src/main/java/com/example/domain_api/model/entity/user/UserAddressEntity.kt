package com.example.domain_api.model.entity.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserAddressEntity(
    @PrimaryKey
    val uuid: String,
    val street: String,
    val house: String,
    val flat: String?,
    val entrance: String?,
    val floor: String?,
    val comment: String?,
    val streetUuid: String,
    val userUuid: String?,
)