package com.bunbeauty.papakarlo.utils.product

import com.bunbeauty.data.model.CartProduct
import com.bunbeauty.data.model.Delivery
import com.bunbeauty.data.model.MenuProduct
import com.bunbeauty.papakarlo.utils.string.IStringHelper
import javax.inject.Inject

class ProductHelper @Inject constructor(private val stringHelper: IStringHelper) : IProductHelper {

    override fun getMenuProductPriceString(menuProduct: com.bunbeauty.data.model.MenuProduct): String {
        return stringHelper.toStringPrice(getMenuProductPrice(menuProduct))
    }

    override fun getMenuProductOldPriceString(menuProduct: com.bunbeauty.data.model.MenuProduct): String {
        return if (menuProduct.discountCost == null) {
            ""
        } else {
            stringHelper.toStringPrice(menuProduct.cost)
        }
    }

    override fun getCartProductPriceString(cartProduct: com.bunbeauty.data.model.CartProduct): String {
        return stringHelper.toStringPrice(getCartProductPrice(cartProduct))
    }

    override fun getCartProductOldPriceString(cartProduct: com.bunbeauty.data.model.CartProduct): String {
        return if (cartProduct.menuProduct.discountCost == null) {
            ""
        } else {
            stringHelper.toStringPrice(cartProduct.menuProduct.cost * cartProduct.count)
        }
    }

    override fun getFullPriceString(cartProductList: List<com.bunbeauty.data.model.CartProduct>): String {
        return stringHelper.toStringPrice(getFullPrice(cartProductList))
    }

    override fun getFullPriceStringWithDelivery(
        cartProductList: List<com.bunbeauty.data.model.CartProduct>,
        delivery: com.bunbeauty.data.model.Delivery
    ): String {
        return if (getDifferenceBeforeFreeDelivery(cartProductList, delivery.forFree) > 0) {
            stringHelper.toStringPrice(getFullPrice(cartProductList) + delivery.cost)
        } else {
            getFullPriceString(cartProductList)
        }
    }

    override fun getDifferenceBeforeFreeDeliveryString(
        cartProductList: List<com.bunbeauty.data.model.CartProduct>,
        priceForFreeDelivery: Int
    ): String {
        return if (getDifferenceBeforeFreeDelivery(cartProductList, priceForFreeDelivery) > 0) {
            stringHelper.toStringPrice(priceForFreeDelivery - getFullPrice(cartProductList))
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