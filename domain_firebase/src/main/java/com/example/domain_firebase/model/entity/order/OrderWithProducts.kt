package com.example.domain_firebase.model.entity.order

import androidx.room.Embedded
import androidx.room.Relation
import com.example.domain_firebase.model.entity.product.CartProductEntity
import com.example.domain_firebase.model.entity.product.OrderProductEntity

data class OrderWithProducts(

    @Embedded
    val order: OrderEntity,

    @Relation(parentColumn = "uuid", entityColumn = "orderUuid")
    val orderProductList: List<OrderProductEntity>
)