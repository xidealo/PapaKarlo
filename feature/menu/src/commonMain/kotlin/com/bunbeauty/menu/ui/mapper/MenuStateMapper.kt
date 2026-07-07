package com.bunbeauty.menu.ui.mapper

import com.bunbeauty.core.Constants.RUBLE_CURRENCY
import com.bunbeauty.core.model.CategoryItem
import com.bunbeauty.core.model.MenuItem
import com.bunbeauty.core.model.ProductUi
import com.bunbeauty.designsystem.ui.element.TopCartUi
import com.bunbeauty.menu.presentation.MENU_GRID_INDEX_FAVORITES
import com.bunbeauty.menu.presentation.MenuState
import com.bunbeauty.menu.presentation.menuContentStartGridIndex
import com.bunbeauty.menu.ui.state.MenuItemUi
import com.bunbeauty.menu.ui.state.MenuViewState
import kotlinx.collections.immutable.toImmutableList

fun MenuState.DataState.mapState(): MenuViewState {
    val favoriteProducts =
        favoriteProductList
            .map { menuProduct ->
                menuProduct.toMenuProductItemUi()
            }.map { menuItemUi ->
                menuItemUi.product
            }.toImmutableList()

    return MenuViewState(
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
        favoriteProductList = favoriteProducts,
        hasFavoritesSection = favoriteProducts.isNotEmpty(),
        state =
            when (state) {
                MenuState.DataState.State.LOADING -> MenuViewState.State.Loading
                MenuState.DataState.State.SUCCESS -> MenuViewState.State.Success
                MenuState.DataState.State.ERROR -> MenuViewState.State.Error
            },
        userScrollEnabled = userScrollEnabled,
        lastOrder = lastOrder,
        scrollToTopRequest = scrollToTopRequest,
    )
}

fun getMenuListPosition(
    categoryItem: CategoryItem,
    menuItemList: List<MenuItemUi>,
    hasFavoritesSection: Boolean,
): Int {
    if (categoryItem.uuid == com.bunbeauty.core.Constants.FAVORITES_CATEGORY_UUID) {
        return MENU_GRID_INDEX_FAVORITES
    }

    val indexInMenuList =
        menuItemList.indexOfFirst { menuItem ->
            (menuItem as? MenuItemUi.CategoryHeader)?.uuid == categoryItem.uuid
        }
    val startIndex = menuContentStartGridIndex(hasFavoritesSection = hasFavoritesSection)
    return if (indexInMenuList < 0) {
        startIndex
    } else {
        indexInMenuList + startIndex
    }
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
