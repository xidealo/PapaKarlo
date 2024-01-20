package com.bunbeauty

import com.bunbeauty.shared.domain.model.addition.Addition
import com.bunbeauty.shared.domain.model.addition.AdditionGroup
import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.model.addition.CartProductAddition
import com.bunbeauty.shared.domain.model.category.Category
import com.bunbeauty.shared.domain.model.product.MenuProduct

fun getMenuProduct(
    uuid: String = "1",
    newPrice: Int = 0,
    oldPrice: Int? = null,
    categoryList: List<Category> = emptyList(),
    isRecommended: Boolean = false,
    visible: Boolean = true,
    additionGroups: List<AdditionGroup> = emptyList(),
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
    isRecommended = isRecommended,
    additionGroups = additionGroups,
)

fun getCartProduct(
    uuid: String = "1",
    count: Int = 0,
    menuProduct: MenuProduct,
    cartProductAdditionList: List<CartProductAddition> = emptyList(),
) = CartProduct(
    uuid = uuid,
    count = count,
    product = menuProduct,
    additionList = cartProductAdditionList
)

fun getCategoryProduct(uuid: String, name: String = "", priority: Int = 0) = Category(
    uuid = uuid,
    name = name,
    priority = priority
)

fun getCartProductAddition(uuid: String = "1", additionUuid: String = "1", price: Int? = 100) =
    CartProductAddition(
        uuid = uuid,
        name = "",
        fullName = null,
        price = price,
        cartProductUuid = "",
        additionUuid = additionUuid,
        priority = null
    )

fun getAddition(
    uuid: String = "1",
    priority: Int = 0,
    additionGroupUuid: String = "",
    isVisible: Boolean = true,
    price: Int? = 0,
) = Addition(
    uuid = uuid,
    name = "",
    fullName = null,
    price = price,
    isSelected = false,
    isVisible = isVisible,
    photoLink = "",
    additionGroupUuid = additionGroupUuid,
    priority = priority
)

fun getAdditionGroup(
    uuid: String = "1",
    priority: Int = 0,
    additions: List<Addition> = emptyList(),
    isVisible: Boolean = true,
) = AdditionGroup(
    uuid = uuid,
    name = "",
    isVisible = isVisible,
    priority = priority,
    additionList = additions,
    singleChoice = false
)
