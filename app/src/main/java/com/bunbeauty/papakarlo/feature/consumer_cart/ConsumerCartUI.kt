package com.bunbeauty.papakarlo.feature.consumer_cart

data class ConsumerCartUI(
    val forFreeDelivery: String,
    val cartProductList: List<CartProductItem>,
    val oldTotalCost: String?,
    val newTotalCost: String,
)
