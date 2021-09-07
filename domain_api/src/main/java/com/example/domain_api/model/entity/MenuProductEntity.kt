package com.example.domain_api.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MenuProductEntity(
    @PrimaryKey
    val uuid: String,
    val name: String,
    val cost: Int,
    val discountCost: Int?,
    val weight: Int?,
    val description: String,
    val comboDescription: String?,
    val photoLink: String,
    val productCode: String,
    val barcode: Int?,
    val visible: Boolean
)