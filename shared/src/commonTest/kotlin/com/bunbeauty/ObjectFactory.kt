package com.bunbeauty

import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.model.category.Category
import com.bunbeauty.shared.domain.model.product.MenuProduct

fun getMenuProduct(
    uuid: String = "1",
    newPrice: Int = 0,
    oldPrice: Int? = null,
    categoryList: List<Category> = emptyList(),
    isRecommended: Boolean = false,
    visible: Boolean = true,
) = MenuProduct(
    uuid = uuid,
    name = "Kapusta",
    newPrice = newPrice,
    oldPrice = oldPrice,
    utils = "г",
    nutrition = 1,
    description = "",
    comboDescription = "",
    photoLink = "",
    categoryList = categoryList,
    visible = visible,
    isRecommended = isRecommended
)

fun getCartProduct(uuid: String = "1", count: Int = 0, menuProduct: MenuProduct) = CartProduct(
    uuid = uuid,
    count = count,
    product = menuProduct,
)

fun getCategoryProduct(uuid: String, name: String = "", priority: Int = 0) = Category(
    uuid = uuid,
    name = name,
    priority = priority
)
