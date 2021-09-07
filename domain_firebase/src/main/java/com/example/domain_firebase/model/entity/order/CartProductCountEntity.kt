package com.example.domain_firebase.model.entity.order

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartProductCountEntity(
    @PrimaryKey
    val uuid: String,
    val count: Int,
)