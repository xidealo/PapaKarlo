package com.bunbeauty.domain.model.firebase

class OrderFirebase(
    var orderEntity: OrderEntityFirebase = OrderEntityFirebase(),
    var cartProducts: List<CartProductFirebase> = ArrayList(),
    var timestamp: Long? = null
)