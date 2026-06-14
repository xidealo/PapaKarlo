package com.bunbeauty.menu.ui.mapper

import com.bunbeauty.core.Constants.RUBLE_CURRENCY
import com.bunbeauty.core.model.CategoryItem
import com.bunbeauty.core.model.MenuItem
import com.bunbeauty.core.model.ProductUi
import com.bunbeauty.designsystem.ui.element.TopCartUi
import com.bunbeauty.menu.presentation.MENU_FIRST_CONTENT_GRID_INDEX
import com.bunbeauty.menu.presentation.MenuState
import com.bunbeauty.menu.ui.state.MenuItemUi
import com.bunbeauty.menu.ui.state.MenuViewState
import kotlinx.collections.immutable.toImmutableList

fun MenuState.DataState.mapState(): MenuViewState =
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
        state =
            when (state) {
                MenuState.DataState.State.LOADING -> MenuViewState.State.Loading
                MenuState.DataState.State.SUCCESS -> MenuViewState.State.Success
                MenuState.DataState.State.ERROR -> MenuViewState.State.Error
            },
        userScrollEnabled = userScrollEnabled,
        lastOrder = lastOrder,
    )

fun getMenuListPosition(
    categoryItem: CategoryItem,
    menuItemList: List<MenuItemUi>,
): Int {
    val indexInMenuList =
        menuItemList.indexOfFirst { menuItem ->
            (menuItem as? MenuItemUi.CategoryHeader)?.uuid == categoryItem.uuid
        }
    return if (indexInMenuList < 0) 0 else indexInMenuList + MENU_FIRST_CONTENT_GRID_INDEX
}

fun MenuItem.Product.toMenuProductItemUi(): MenuItemUi.Product {
    val productKey = "MenuProductItem_${categoryUuid}_$uuid"
    return MenuItemUi.Product(
        key = productKey,
        product =
            ProductUi(
                key = productKey,
                uuid = uuid,
                photoLink = photoLink,
                name = name,
                oldPrice = oldPrice,
                newPrice = newPrice,
            ),
    )
}

private fun MenuItem.toMenuItemUi(): MenuItemUi =
    when (this) {
        is MenuItem.CategoryHeader -> {
            MenuItemUi.CategoryHeader(
                key = "CategoryHeader $uuid",
                uuid = uuid,
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
