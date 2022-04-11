package com.bunbeauty.domain.model.menu

import com.bunbeauty.domain.model.category.Category
import com.bunbeauty.domain.model.product.MenuProduct

data class MenuSection(
    val category: Category,
    val menuProductList: List<MenuProduct>
)
