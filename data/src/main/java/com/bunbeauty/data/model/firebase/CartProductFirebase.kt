package com.bunbeauty.data.model.firebase


data class CartProductFirebase(
    var menuProduct: MenuProductFirebase = MenuProductFirebase(),
    var count: Int = 1,
    var orderId: String? = null
)