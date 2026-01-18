package com.bunbeauty.core.model.cart

import com.bunbeauty.core.model.addition.CartProductAddition

data class LightCartProduct(
    val uuid: String,
    val name: String,
    val newCost: Int,
    val oldCost: Int?,
    val photoLink: String,
    val count: Int,
    val menuProductUuid: String,
    val cartProductAdditionList: List<CartProductAddition>,
)
