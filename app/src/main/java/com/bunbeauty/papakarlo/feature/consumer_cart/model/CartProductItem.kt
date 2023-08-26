package com.bunbeauty.papakarlo.feature.consumer_cart.model

data class CartProductItem(
    val uuid: String,
    val name: String,
    val newCost: String,
    val oldCost: String?,
    val photoLink: String,
    val count: Int,
    val menuProductUuid: String
)
