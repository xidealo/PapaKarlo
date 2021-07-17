package com.bunbeauty.domain.util.product

import com.bunbeauty.domain.model.local.CartProduct
import com.bunbeauty.domain.model.local.Delivery
import com.bunbeauty.domain.model.local.MenuProduct

interface IProductHelper {

    fun getFullPriceString(cartProductList: List<CartProduct>): String
    fun getNewTotalCost(cartProductList: List<CartProduct>): Int
    fun getOldTotalCost(cartProductList: List<CartProduct>): Int?

    fun getCartProductNewCost(cartProduct: CartProduct): Int
    fun getCartProductOldCost(cartProduct: CartProduct): Int?

    fun getMenuProductNewPrice(menuProduct: MenuProduct): Int

    fun getTotalCount(cartProductList: List<CartProduct>): Int

    @Deprecated("use method which returns Int")
    fun getFullPriceStringWithDelivery(
        cartProductList: List<CartProduct>,
        delivery: Delivery
    ): String

    @Deprecated("use method which returns Int")
    fun getDifferenceBeforeFreeDeliveryString(
        cartProductList: List<CartProduct>,
        priceForFreeDelivery: Int
    ): String

    @Deprecated("use method which returns Int")
    fun getCartProductPriceString(cartProduct: CartProduct): String

    @Deprecated("use method which returns Int")
    fun getCartProductOldPriceString(cartProduct: CartProduct): String

    @Deprecated("use method which returns Int")
    fun getMenuProductPriceString(menuProduct: MenuProduct): String

    @Deprecated("use method which returns Int")
    fun getMenuProductOldPriceString(menuProduct: MenuProduct): String

}