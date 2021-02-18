package com.example.domain_firebase.model.entity.product

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MenuProductEntity(
    @PrimaryKey
    val uuid: String,
    val name: String,
    val newPrice: Int,
    val oldPrice: Int?,
    val utils: String?,
    val nutrition: Int?,
    val description: String,
    val comboDescription: String?,
    val photoLink: String,
    val productCode: String,
    val barcode: Int?,
    val visible: Boolean
)