package com.bunbeauty

import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.model.product.MenuProduct


fun getMenuProduct(newPrice: Int = 0, oldPrice: Int = 0) = MenuProduct(
    uuid = "1",
    name = "Kapusta",
    newPrice = newPrice,
    oldPrice = oldPrice,
    utils = "г",
    nutrition = 1,
    description = "",
    comboDescription = "",
    photoLink = "",
    categoryList = emptyList(),
    visible = true,
)


fun getCartProduct(count: Int = 0, menuProduct: MenuProduct = getMenuProduct()) = CartProduct(
    uuid = "1",
    count = count,
    product = menuProduct,
)