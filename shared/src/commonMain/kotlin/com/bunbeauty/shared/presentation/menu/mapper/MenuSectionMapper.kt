package com.bunbeauty.shared.presentation.menu.mapper

import com.bunbeauty.shared.domain.model.menu.MenuSection
import com.bunbeauty.shared.presentation.menu.model.MenuItem

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
