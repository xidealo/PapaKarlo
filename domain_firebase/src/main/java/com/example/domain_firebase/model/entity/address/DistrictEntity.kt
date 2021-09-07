package com.example.domain_firebase.model.entity.address

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.domain_firebase.model.entity.cafe.CafeEntity

@Entity(
    foreignKeys = [ForeignKey(
        entity = CafeEntity::class,
        parentColumns = ["uuid"],
        childColumns = ["cafeUuid"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class DistrictEntity(
    @PrimaryKey
    var uuid: String,
    var name: String,
    var cafeUuid: String
)