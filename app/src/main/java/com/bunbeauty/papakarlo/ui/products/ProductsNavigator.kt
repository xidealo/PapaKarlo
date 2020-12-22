package com.bunbeauty.papakarlo.ui.products

import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.ui.base.BaseNavigator

interface ProductsNavigator: BaseNavigator {
    fun goToProduct(menuProduct: MenuProduct)
}