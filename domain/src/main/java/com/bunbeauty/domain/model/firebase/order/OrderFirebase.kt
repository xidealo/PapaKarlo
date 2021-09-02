package com.bunbeauty.domain.model.firebase.order

import com.bunbeauty.domain.model.firebase.OrderProductFirebase

class OrderFirebase(
    var orderEntity: OrderEntityFirebase = OrderEntityFirebase(),
    var cartProducts: List<OrderProductFirebase> = emptyList()
)