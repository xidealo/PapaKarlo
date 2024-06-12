package com.bunbeauty.shared.domain.model.cart

data class CartTotal(
    val discount: Int?,
    val deliveryCost: Int?,
    val oldTotalCost: Int?,
    val newTotalCost: Int,
    val oldFinalCost: Int?,
    val newFinalCost: Int,
)