package com.bunbeauty.domain.product

import com.bunbeauty.data.model.CartProduct
import com.bunbeauty.data.model.Delivery
import com.bunbeauty.data.model.MenuProduct

interface IProductHelper {

    fun getFullPriceString(cartProductList: List<CartProduct>): String
    fun getFullPrice(cartProductList: List<CartProduct>): Int
    fun getFullPriceStringWithDelivery(
        cartProductList: List<CartProduct>,
        delivery: Delivery
    ): String

    fun getDifferenceBeforeFreeDeliveryString(
        cartProductList: List<CartProduct>,
        priceForFreeDelivery: Int
    ): String

    fun getCartProductPriceString(cartProduct: CartProduct): String
    fun getCartProductOldPriceString(cartProduct: CartProduct): String

    fun getMenuProductPriceString(menuProduct: MenuProduct): String
    fun getMenuProductOldPriceString(menuProduct: MenuProduct): String

}