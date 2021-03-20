package com.bunbeauty.papakarlo.ui.products

import com.bunbeauty.data.model.MenuProduct

interface ProductsNavigator {
    fun goToProduct(menuProduct: com.bunbeauty.data.model.MenuProduct)
}