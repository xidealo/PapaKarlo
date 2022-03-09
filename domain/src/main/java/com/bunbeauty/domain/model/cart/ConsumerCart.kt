package com.bunbeauty.domain.model.cart

data class ConsumerCart(
    val forFreeDelivery: Int,
    val cartProductList: List<LightCartProduct>,
    val oldTotalCost: Int?,
    val newTotalCost: Int,
)