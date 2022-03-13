package com.bunbeauty.domain.model.cart

data class CartTotal(
    val totalCost: Int,
    val deliveryCost: Int,
    val amountToPay: Int,
)
