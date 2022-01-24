package com.example.domain_firebase.model.entity.address

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.domain_firebase.model.entity.user.UserEntity

@Entity(
    indices = [Index(value = ["userUuid"])],
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["uuid"],
        childColumns = ["userUuid"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = StreetEntity::class,
        parentColumns = ["uuid"],
        childColumns = ["streetUuid"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class UserAddressEntity(
    @PrimaryKey
    val uuid: String,
    val house: String,
    val flat: String?,
    val entrance: String?,
    val floor: String?,
    val comment: String?,
    val streetUuid: String,
    val userUuid: String,
)