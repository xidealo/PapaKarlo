package com.example.domain_firebase.model.entity.product

import androidx.room.Embedded
import androidx.room.Relation

data class CartProductWithMenuProduct(
    @Embedded
    val cartProductEntity: CartProductEntity,

    @Relation(parentColumn = "menuProductUuid", entityColumn = "uuid")
    val menuProductEntity: MenuProductEntity
)
