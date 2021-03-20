package com.bunbeauty.data.model.order

import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Relation

data class Order(
    @Embedded
    var orderEntity: OrderEntity = OrderEntity(),

    @Relation(parentColumn = "id", entityColumn = "orderId")
    var cartProducts: List<com.bunbeauty.data.model.CartProduct> = ArrayList(),

    @Ignore
    var cafeId: String = "",

    @Ignore
    var timestamp: Map<String, String>? = null

) : com.bunbeauty.data.model.BaseModel()