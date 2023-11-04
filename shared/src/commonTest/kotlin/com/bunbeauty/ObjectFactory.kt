package com.bunbeauty

import com.bunbeauty.shared.domain.model.RecommendationProduct
import com.bunbeauty.shared.domain.model.RecommendationProductList
import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.model.category.Category
import com.bunbeauty.shared.domain.model.product.MenuProduct

fun getMenuProduct(
    uuid: String = "1",
    newPrice: Int = 0,
    oldPrice: Int? = null,
    categoryList: List<Category> = emptyList(),
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
    visible = true,
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

fun getRecommendationProductList(
    maxVisibleCount: Int = 0,
    recommendationProductList: List<RecommendationProduct> = emptyList(),
) = RecommendationProductList(
    maxVisibleCount = maxVisibleCount,
    recommendationProductList = recommendationProductList
)

fun getRecommendationProduct(
    uuid: String,
    menuProduct: MenuProduct,
) = RecommendationProduct(
    uuid = uuid,
    menuProduct = menuProduct,
)