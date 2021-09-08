package com.example.domain_api.model.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = MenuProductEntity::class,
        parentColumns = ["uuid"],
        childColumns = ["menuProductUuid"]
    )]
)
data class CartProductEntity(
    @PrimaryKey
    val uuid: String,
    val count: Int,
    val menuProductUuid: String
)
