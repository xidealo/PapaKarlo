package com.bunbeauty.shared.domain.model.cart

data class CartProductAddition(
    val uuid: String,
    val name: String,
    val price: Int?,
    val cartProductUuid: String,
    val additionUuid: String,
)