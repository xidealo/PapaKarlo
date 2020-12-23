package com.bunbeauty.papakarlo.ui.products

import com.bunbeauty.papakarlo.data.model.MenuProduct

interface ProductsNavigator {
    fun goToProduct(menuProduct: MenuProduct)
}