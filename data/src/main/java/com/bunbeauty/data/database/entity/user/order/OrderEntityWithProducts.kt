package com.bunbeauty.data.database.entity.user.order

import androidx.room.Embedded
import androidx.room.Relation

data class OrderEntityWithProducts(

    @Embedded
    val orderEntity: OrderEntity,

    @Relation(parentColumn = "uuid", entityColumn = "orderUuid")
    val orderProductList: List<OrderProductEntity>
)
