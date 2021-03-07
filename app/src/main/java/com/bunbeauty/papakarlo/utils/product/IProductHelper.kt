package com.bunbeauty.papakarlo.utils.product

import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.data.model.MenuProduct

interface IProductHelper {

    fun getFullPriceString(cartProductList: List<CartProduct>): String

    fun getCartProductPriceString(cartProduct: CartProduct): String
    fun getCartProductOldPriceString(cartProduct: CartProduct): String

    fun getMenuProductPriceString(menuProduct: MenuProduct): String
    fun getMenuProductOldPriceString(menuProduct: MenuProduct): String

}