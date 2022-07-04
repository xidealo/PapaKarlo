package com.bunbeauty.shared.domain.model.cart

data class CartWithProducts(
    val forFreeDelivery: Int,
    val cartProductList: List<LightCartProduct>,
    val oldTotalCost: Int?,
    val newTotalCost: Int,
)