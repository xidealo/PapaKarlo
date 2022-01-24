package com.example.domain_firebase.model.firebase.order

class OrderFirebase(
    var orderEntity: OrderEntityFirebase = OrderEntityFirebase(),
    var cartProducts: List<com.example.domain_firebase.model.firebase.OrderProductFirebase> = emptyList()
)