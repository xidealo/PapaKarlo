package com.bunbeauty.shared.domain.model.cart

sealed class ConsumerCart {
    object Empty : ConsumerCart()
    data class WithProducts(
        val forFreeDelivery: Int,
        val cartProductList: List<LightCartProduct>,
        val oldTotalCost: Int?,
        val newTotalCost: Int,
    ) : ConsumerCart()
}