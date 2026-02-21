package com.bunbeauty.core.model.menu

import com.bunbeauty.core.model.category.Category
import com.bunbeauty.core.model.product.MenuProduct

data class MenuSection(
    val category: Category,
    val menuProductList: List<MenuProduct>,
)
