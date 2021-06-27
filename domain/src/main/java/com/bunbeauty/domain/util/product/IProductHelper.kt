package com.bunbeauty.domain.util.product

import com.bunbeauty.domain.model.CartProduct
import com.bunbeauty.domain.model.Delivery
import com.bunbeauty.domain.model.MenuProduct

interface IProductHelper {

    fun getFullPriceString(cartProductList: List<CartProduct>): String
    fun getNewTotalCost(cartProductList: List<CartProduct>): Int
    fun getOldTotalCost(cartProductList: List<CartProduct>): Int?

    fun getCartProductNewCost(cartProduct: CartProduct): Int
    fun getCartProductOldCost(cartProduct: CartProduct): Int?

    fun getMenuProductNewPrice(menuProduct: MenuProduct): Int

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