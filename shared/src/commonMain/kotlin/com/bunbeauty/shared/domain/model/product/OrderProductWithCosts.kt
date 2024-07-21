package com.bunbeauty.shared.domain.model.product

data class OrderProductWithCosts(
    val uuid: String,
    val count: Int,
    val newCost: Int,
    val oldCost: Int?,
    val product: OrderMenuProduct
)
