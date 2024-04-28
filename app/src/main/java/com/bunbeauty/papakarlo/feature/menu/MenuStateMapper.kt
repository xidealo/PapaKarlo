package com.bunbeauty.papakarlo.feature.menu

import com.bunbeauty.papakarlo.feature.menu.model.MenuItemUi
import com.bunbeauty.papakarlo.feature.menu.model.MenuViewState
import com.bunbeauty.papakarlo.feature.topcart.TopCartUi
import com.bunbeauty.shared.Constants.RUBLE_CURRENCY
import com.bunbeauty.shared.presentation.menu.model.MenuItem
import com.bunbeauty.shared.presentation.menu.model.MenuDataState
import kotlinx.collections.immutable.toImmutableList

fun MenuDataState.toMenuUi(): MenuViewState {
    return MenuViewState(
        topCartUi = TopCartUi(
            cost = cartCostAndCount?.cost?.let { cost ->
                "$cost $RUBLE_CURRENCY"
            } ?: "",
            count = cartCostAndCount?.count ?: ""
        ),
        categoryItemList = categoryItemList.toImmutableList(),
        menuItemList = menuItemList.map { menuItem ->
            menuItem.toMenuItemUi()
        }.toImmutableList(),
        state = state,
        userScrollEnabled = userScrollEnabled,
        eventList = eventList.toImmutableList()
    )
}

fun MenuItem.Product.toMenuProductItemUi(): MenuItemUi.Product {
    return MenuItemUi.Product(
        key = "MenuProductItem $categoryUuid $uuid",
        uuid = uuid,
        photoLink = photoLink,
        name = name,
        oldPrice = oldPrice,
        newPrice = newPrice,
    )
}

private fun MenuItem.toMenuItemUi(): MenuItemUi {
    return when (this) {
        is MenuItem.CategoryHeader -> {
            MenuItemUi.CategoryHeader(
                key = "CategoryHeader $uuid",
                name = name,
            )
        }

        is MenuItem.Product -> {
            toMenuProductItemUi()
        }

        is MenuItem.Discount -> {
            MenuItemUi.Discount(
                key = "Discount",
                discount = discount,
            )
        }
    }
}
