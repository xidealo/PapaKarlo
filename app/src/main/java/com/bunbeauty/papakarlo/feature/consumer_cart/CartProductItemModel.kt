package com.bunbeauty.papakarlo.feature.consumer_cart

data class CartProductItemModel(
    val uuid: String,
    val name: String,
    val newCost: String,
    val oldCost: String?,
    val photoLink: String,
    val count: Int,
    val menuProductUuid: String,
)
