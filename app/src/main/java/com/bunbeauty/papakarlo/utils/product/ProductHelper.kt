package com.bunbeauty.papakarlo.utils.product

import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.data.model.Delivery
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.utils.string.IStringHelper
import javax.inject.Inject

class ProductHelper @Inject constructor(private val stringHelper: IStringHelper) : IProductHelper {

    override fun getMenuProductPriceString(menuProduct: MenuProduct): String {
        return stringHelper.toStringPrice(getMenuProductPrice(menuProduct))
    }

    override fun getMenuProductOldPriceString(menuProduct: MenuProduct): String {
        return if (menuProduct.discountCost == null) {
            ""
        } else {
            stringHelper.toStringPrice(menuProduct.cost)
        }
    }

    override fun getCartProductPriceString(cartProduct: CartProduct): String {
        return stringHelper.toStringPrice(getCartProductPrice(cartProduct))
    }

    override fun getCartProductOldPriceString(cartProduct: CartProduct): String {
        return if (cartProduct.menuProduct.discountCost == null) {
            ""
        } else {
            stringHelper.toStringPrice(cartProduct.menuProduct.cost * cartProduct.count)
        }
    }

    override fun getFullPriceString(cartProductList: List<CartProduct>): String {
        return stringHelper.toStringPrice(getFullPrice(cartProductList))
    }

    override fun getFullPriceStringWithDelivery(
        cartProductList: List<CartProduct>,
        delivery: Delivery
    ): String {
        return if (getDifferenceBeforeFreeDelivery(cartProductList, delivery.forFree) > 0) {
            stringHelper.toStringPrice(getFullPrice(cartProductList) + delivery.cost)
        } else {
            getFullPriceString(cartProductList)
        }
    }

    override fun getDifferenceBeforeFreeDeliveryString(
        cartProductList: List<CartProduct>,
        priceForFreeDelivery: Int
    ): String {
        return if (getDifferenceBeforeFreeDelivery(cartProductList, priceForFreeDelivery) > 0) {
            stringHelper.toStringPrice(priceForFreeDelivery - getFullPrice(cartProductList))
        } else {
            ""
        }
    }

    fun getMenuProductPrice(menuProduct: MenuProduct): Int {
        return menuProduct.discountCost ?: menuProduct.cost
    }

    fun getCartProductPrice(cartProduct: CartProduct): Int {
        return getMenuProductPrice(cartProduct.menuProduct) * cartProduct.count
    }

    fun getFullPrice(cartProductList: List<CartProduct>): Int {
        return cartProductList.map { cartProduct ->
            getCartProductPrice(cartProduct)
        }.sum()
    }

    fun getDifferenceBeforeFreeDelivery(
        cartProductList: List<CartProduct>,
        priceForFreeDelivery: Int
    ): Int {
        return priceForFreeDelivery - getFullPrice(cartProductList)
    }


}