package com.bunbeauty.menu.ui.mapper

import com.bunbeauty.core.Constants.RUBLE_CURRENCY
import com.bunbeauty.core.model.MenuItem
import com.bunbeauty.designsystem.ui.element.TopCartUi
import com.bunbeauty.menu.ui.state.MenuViewState
import com.bunbeauty.menu.presentation.model.MenuDataState
import com.bunbeauty.menu.ui.state.MenuItemUi
import kotlinx.collections.immutable.toImmutableList

fun MenuDataState.toMenuViewState(): MenuViewState =
    MenuViewState(
        topCartUi =
            TopCartUi(
                cost =
                    cartCostAndCount?.cost?.let { cost ->
                        "$cost $RUBLE_CURRENCY"
                    } ?: "",
                count = cartCostAndCount?.count ?: "",
            ),
        categoryItemList = categoryItemList.toImmutableList(),
        menuItemList =
            menuItemList
                .map { menuItem ->
                    menuItem.toMenuItemUi()
                }.toImmutableList(),
        state = state,
        userScrollEnabled = userScrollEnabled,
        eventList = eventList.toImmutableList(),
    )

fun MenuItem.Product.toMenuProductItemUi(): MenuItemUi.Product =
    MenuItemUi.Product(
        key = "MenuProductItem $categoryUuid $uuid",
        uuid = uuid,
        photoLink = photoLink,
        name = name,
        oldPrice = oldPrice,
        newPrice = newPrice,
    )

private fun MenuItem.toMenuItemUi(): MenuItemUi =
    when (this) {
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
