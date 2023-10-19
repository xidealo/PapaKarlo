package com.bunbeauty.shared.presentation.consumercart

data class ConsumerCartUI(
    val forFreeDelivery: String,
    val cartProductList: List<CartProductItem>,
    val oldTotalCost: String?,
    val newTotalCost: String
)
