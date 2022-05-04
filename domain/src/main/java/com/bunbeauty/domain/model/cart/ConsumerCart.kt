package com.bunbeauty.domain.model.cart

//data class ConsumerCart(
//    val forFreeDelivery: Int,
//    val cartProductList: List<LightCartProduct>,
//    val oldTotalCost: Int?,
//    val newTotalCost: Int,
//)

sealed class ConsumerCart {
    object Empty : ConsumerCart()
    data class WithProducts(
        val forFreeDelivery: Int,
        val cartProductList: List<LightCartProduct>,
        val oldTotalCost: Int?,
        val newTotalCost: Int,
    ) : ConsumerCart()
}