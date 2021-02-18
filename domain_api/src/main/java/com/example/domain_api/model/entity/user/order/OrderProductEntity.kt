package com.example.domain_api.model.entity.user.order

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = OrderEntity::class,
        parentColumns = ["uuid"],
        childColumns = ["orderUuid"]
    )]
)
class OrderProductEntity(

    @PrimaryKey
    val uuid: String,
    val count: Int,
    val name: String,
    val newPrice: Int,
    val oldPrice: Int?,
    val utils: String?,
    val nutrition: Int?,
    val description: String,
    val comboDescription: String?,
    val photoLink: String,
    val barcode: Int?,
    val orderUuid: String
)