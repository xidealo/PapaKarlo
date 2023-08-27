package com.bunbeauty.papakarlo.feature.consumercart.model

data class ConsumerCartUI(
    val forFreeDelivery: String,
    val cartProductList: List<CartProductItem>,
    val oldTotalCost: String?,
    val newTotalCost: String
)
