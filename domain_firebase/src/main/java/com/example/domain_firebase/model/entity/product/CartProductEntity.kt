package com.example.domain_firebase.model.entity.product

import androidx.room.*
import com.example.domain_firebase.model.entity.address.DistrictEntity
import com.example.domain_firebase.model.entity.order.OrderEntity

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