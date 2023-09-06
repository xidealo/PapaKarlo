package com.bunbeauty.shared.domain.model.cart

data class CartTotal(
    val totalCost: Int,
    val oldFinalCost: Int?,
    val deliveryCost: Int,
    val newFinalCost: Int
)