package com.bunbeauty.domain.product

import com.bunbeauty.domain.string_helper.IStringHelper
import javax.inject.Inject

class ProductHelper @Inject constructor(private val stringHelper: IStringHelper) : IProductHelper {

    override fun getMenuProductPriceString(menuProduct: com.bunbeauty.data.model.MenuProduct): String {
        return stringHelper.toStringCost(getMenuProductPrice(menuProduct))
    }

    override fun getMenuProductOldPriceString(menuProduct: com.bunbeauty.data.model.MenuProduct): String {
        return if (menuProduct.discountCost == null) {
            ""
        } else {
            stringHelper.toStringCost(menuProduct.cost)
        }
    }

    override fun getCartProductPriceString(cartProduct: com.bunbeauty.data.model.CartProduct): String {
        return stringHelper.toStringCost(getCartProductPrice(cartProduct))
    }

    override fun getCartProductOldPriceString(cartProduct: com.bunbeauty.data.model.CartProduct): String {
        return if (cartProduct.menuProduct.discountCost == null) {
            ""
        } else {
            stringHelper.toStringCost(cartProduct.menuProduct.cost * cartProduct.count)
        }
    }

    override fun getFullPriceString(cartProductList: List<com.bunbeauty.data.model.CartProduct>): String {
        return stringHelper.toStringCost(getFullPrice(cartProductList))
    }

    override fun getFullPriceStringWithDelivery(
        cartProductList: List<com.bunbeauty.data.model.CartProduct>,
        delivery: com.bunbeauty.data.model.Delivery
    ): String {
        return if (getDifferenceBeforeFreeDelivery(cartProductList, delivery.forFree) > 0) {
            stringHelper.toStringCost(getFullPrice(cartProductList) + delivery.cost)
        } else {
            getFullPriceString(cartProductList)
        }
    }

    override fun getDifferenceBeforeFreeDeliveryString(
        cartProductList: List<com.bunbeauty.data.model.CartProduct>,
        priceForFreeDelivery: Int
    ): String {
        return if (getDifferenceBeforeFreeDelivery(cartProductList, priceForFreeDelivery) > 0) {
            stringHelper.toStringCost(priceForFreeDelivery - getFullPrice(cartProductList))
        } else {
            ""
        }
    }

    fun getMenuProductPrice(menuProduct: com.bunbeauty.data.model.MenuProduct): Int {
        return menuProduct.discountCost ?: menuProduct.cost
    }

    fun getCartProductPrice(cartProduct: com.bunbeauty.data.model.CartProduct): Int {
        return getMenuProductPrice(cartProduct.menuProduct) * cartProduct.count
    }

    fun getFullPrice(cartProductList: List<com.bunbeauty.data.model.CartProduct>): Int {
        return cartProductList.map { cartProduct ->
            getCartProductPrice(cartProduct)
        }.sum()
    }

    fun getDifferenceBeforeFreeDelivery(
        cartProductList: List<com.bunbeauty.data.model.CartProduct>,
        priceForFreeDelivery: Int
    ): Int {
        return priceForFreeDelivery - getFullPrice(cartProductList)
    }


}