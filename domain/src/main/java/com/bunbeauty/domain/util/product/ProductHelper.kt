package com.bunbeauty.domain.util.product

import com.bunbeauty.domain.model.local.CartProduct
import com.bunbeauty.domain.model.local.Delivery
import com.bunbeauty.domain.model.local.MenuProduct
import com.bunbeauty.domain.util.string_helper.IStringHelper
import javax.inject.Inject

class ProductHelper @Inject constructor(private val stringHelper: IStringHelper) : IProductHelper {

    override fun getMenuProductPriceString(menuProduct: MenuProduct): String {
        return stringHelper.getCostString(getMenuProductPrice(menuProduct))
    }

    override fun getMenuProductOldPriceString(menuProduct: MenuProduct): String {
        return if (menuProduct.discountCost == null) {
            ""
        } else {
            stringHelper.getCostString(menuProduct.cost)
        }
    }

    override fun getCartProductPriceString(cartProduct: CartProduct): String {
        return stringHelper.getCostString(getCartProductPrice(cartProduct))
    }

    override fun getCartProductOldPriceString(cartProduct: CartProduct): String {
        return if (cartProduct.menuProduct.discountCost == null) {
            ""
        } else {
            stringHelper.getCostString(cartProduct.menuProduct.cost * cartProduct.count)
        }
    }

    override fun getFullPriceString(cartProductList: List<CartProduct>): String {
        return stringHelper.getCostString(getNewTotalCost(cartProductList))
    }

    override fun getFullPriceStringWithDelivery(
        cartProductList: List<CartProduct>,
        delivery: Delivery
    ): String {
        return if (getDifferenceBeforeFreeDelivery(cartProductList, delivery.forFree) > 0) {
            stringHelper.getCostString(getNewTotalCost(cartProductList) + delivery.cost)
        } else {
            getFullPriceString(cartProductList)
        }
    }

    override fun getDifferenceBeforeFreeDeliveryString(
        cartProductList: List<CartProduct>,
        priceForFreeDelivery: Int
    ): String {
        return if (getDifferenceBeforeFreeDelivery(cartProductList, priceForFreeDelivery) > 0) {
            stringHelper.getCostString(priceForFreeDelivery - getNewTotalCost(cartProductList))
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

    override fun getNewTotalCost(cartProductList: List<CartProduct>): Int {
        return cartProductList.map { cartProduct ->
            getCartProductPrice(cartProduct)
        }.sum()
    }

    override fun getOldTotalCost(cartProductList: List<CartProduct>): Int? {
        val hasSomeDiscounts = cartProductList.any { cartProduct ->
            cartProduct.menuProduct.discountCost != null
        }

        return if (hasSomeDiscounts) {
            cartProductList.map { cartProduct ->
                getCartProductOldCost(cartProduct) ?: getCartProductNewCost(cartProduct)
            }.sum()
        } else {
            null
        }
    }

    override fun getCartProductNewCost(cartProduct: CartProduct): Int {
        return getMenuProductNewPrice(cartProduct.menuProduct) * cartProduct.count
    }

    override fun getMenuProductNewPrice(menuProduct: MenuProduct): Int {
        return menuProduct.discountCost ?: menuProduct.cost
    }

    override fun getCartProductOldCost(cartProduct: CartProduct): Int? {
        return if (cartProduct.menuProduct.discountCost == null) {
            null
        } else {
            cartProduct.menuProduct.cost * cartProduct.count
        }
    }

    fun getDifferenceBeforeFreeDelivery(
        cartProductList: List<CartProduct>,
        priceForFreeDelivery: Int
    ): Int {
        return priceForFreeDelivery - getNewTotalCost(cartProductList)
    }


}