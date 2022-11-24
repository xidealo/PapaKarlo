package com.bunbeauty.shared.domain.model.cart

data class CartTotal(
    val totalCost: Int,
    val deliveryCost: Int,
    val finalCost: Int
)