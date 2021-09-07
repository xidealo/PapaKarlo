package com.example.domain_firebase.model.entity.address

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = DistrictEntity::class,
        parentColumns = ["uuid"],
        childColumns = ["districtUuid"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class StreetEntity(
    @PrimaryKey
    var uuid: String,
    var name: String,
    var districtUuid: String
)
