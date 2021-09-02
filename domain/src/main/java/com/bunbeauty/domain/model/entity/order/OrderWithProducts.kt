package com.bunbeauty.domain.model.entity.order

import androidx.room.Embedded
import androidx.room.Relation
import com.bunbeauty.domain.model.entity.product.CartProductEntity
import com.bunbeauty.domain.model.entity.product.OrderProductEntity

data class OrderWithProducts(

    @Embedded
    val order: OrderEntity,

    @Relation(parentColumn = "uuid", entityColumn = "orderUuid")
    val orderProductList: List<OrderProductEntity>
)