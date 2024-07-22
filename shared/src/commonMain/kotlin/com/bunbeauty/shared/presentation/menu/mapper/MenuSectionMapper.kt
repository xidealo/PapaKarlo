package com.bunbeauty.shared.presentation.menu.mapper

import com.bunbeauty.shared.domain.model.menu.MenuSection
import com.bunbeauty.shared.presentation.menu.model.MenuItem

fun MenuSection.toMenuItemList(): List<MenuItem> {
    return buildList {
        add(toMenuCategoryHeaderItem())
        addAll(toMenuProductItemList())
    }
}

private fun MenuSection.toMenuCategoryHeaderItem(): MenuItem.CategoryHeader {
    return MenuItem.CategoryHeader(
        uuid = category.uuid,
        name = category.name
    )
}

private fun MenuSection.toMenuProductItemList(): List<MenuItem.Product> {
    return menuProductList.map { menuProduct ->
        menuProduct.toMenuProductItem(categoryUuid = category.uuid)
    }
}
