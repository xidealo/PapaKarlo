package com.bunbeauty.data.model.firebase

class OrderFirebase(
    var orderEntity: OrderEntityFirebase = OrderEntityFirebase(),
    var cartProducts: List<CartProductFirebase> = ArrayList(),
    var timestamp: Map<String, String>? = null
)