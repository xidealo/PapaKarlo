package com.example.domain_api.model.entity.product

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartProductCount(
    @PrimaryKey
    val uuid: String,
    val count: Int
)
