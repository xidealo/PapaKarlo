package com.bunbeauty.domain.model

import com.bunbeauty.domain.model.category.Category
import com.bunbeauty.domain.model.product.MenuProduct

sealed class MenuModel {

    data class Product(val menuProduct: MenuProduct) : MenuModel()
    data class Section(val category: Category) : MenuModel()

}
