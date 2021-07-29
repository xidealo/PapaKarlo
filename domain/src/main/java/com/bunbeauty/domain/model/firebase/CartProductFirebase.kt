package com.bunbeauty.domain.model.firebase


data class CartProductFirebase(
    var count: Int = 1,
    var menuProduct: MenuProductFirebase = MenuProductFirebase(),
)