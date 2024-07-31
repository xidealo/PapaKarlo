package com.bunbeauty.shared.domain.model.addition

data class CartProductAddition(
    val uuid: String,
    val name: String,
    val fullName: String?,
    val price: Int?,
    val cartProductUuid: String,
    val additionUuid: String,
    val priority: Int?
)
