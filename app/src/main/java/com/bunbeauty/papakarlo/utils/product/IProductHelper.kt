package com.bunbeauty.papakarlo.utils.product

import com.bunbeauty.data.model.CartProduct
import com.bunbeauty.data.model.Delivery
import com.bunbeauty.data.model.MenuProduct

interface IProductHelper {

    fun getFullPriceString(cartProductList: List<com.bunbeauty.data.model.CartProduct>): String
    fun getFullPriceStringWithDelivery(cartProductList: List<com.bunbeauty.data.model.CartProduct>, delivery: com.bunbeauty.data.model.Delivery): String
    fun getDifferenceBeforeFreeDeliveryString(cartProductList: List<com.bunbeauty.data.model.CartProduct>, priceForFreeDelivery: Int): String

    fun getCartProductPriceString(cartProduct: com.bunbeauty.data.model.CartProduct): String
    fun getCartProductOldPriceString(cartProduct: com.bunbeauty.data.model.CartProduct): String

    fun getMenuProductPriceString(menuProduct: com.bunbeauty.data.model.MenuProduct): String
    fun getMenuProductOldPriceString(menuProduct: com.bunbeauty.data.model.MenuProduct): String

}