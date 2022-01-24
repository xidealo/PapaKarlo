package com.example.domain_api.model.entity.user.order

import androidx.room.Embedded
import androidx.room.Relation

data class OrderWithProducts(

    @Embedded
    val order: OrderEntity,

    @Relation(parentColumn = "uuid", entityColumn = "orderUuid")
    val orderProductList: List<OrderProductEntity>
)
