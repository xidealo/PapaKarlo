package com.bunbeauty.domain.model.firebase.order

import com.bunbeauty.domain.model.firebase.CartProductFirebase

class OrderFirebase(
    var orderEntity: OrderEntityFirebase = OrderEntityFirebase(),
    var cartProducts: List<CartProductFirebase> = emptyList()
)