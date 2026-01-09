package com.bunbeauty.core.model.mapper

import com.bunbeauty.core.model.MenuItem
import com.bunbeauty.core.model.menu.MenuSection

fun MenuSection.toMenuItemList(): List<MenuItem> =
    buildList {
        add(toMenuCategoryHeaderItem())
        addAll(toMenuProductItemList())
    }

private fun MenuSection.toMenuCategoryHeaderItem(): MenuItem.CategoryHeader =
    MenuItem.CategoryHeader(
        uuid = category.uuid,
        name = category.name,
    )

private fun MenuSection.toMenuProductItemList(): List<MenuItem.Product> =
    menuProductList.map { menuProduct ->
        menuProduct.toMenuProductItem(categoryUuid = category.uuid)
    }
