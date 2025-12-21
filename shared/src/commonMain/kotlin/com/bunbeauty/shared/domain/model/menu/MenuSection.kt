package com.bunbeauty.shared.domain.model.menu

import com.bunbeauty.shared.domain.model.category.Category
import com.bunbeauty.shared.domain.model.product.MenuProduct

data class MenuSection(
    val category: Category,
    val menuProductList: List<MenuProduct>,
)
