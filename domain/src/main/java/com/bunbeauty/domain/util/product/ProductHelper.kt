package com.bunbeauty.domain.util.product

import com.bunbeauty.domain.model.ui.CartProduct
import com.bunbeauty.domain.model.ui.Delivery
import com.bunbeauty.domain.model.ui.MenuProduct
import com.bunbeauty.domain.util.string_helper.IStringUtil
import javax.inject.Inject

class ProductHelper @Inject constructor(private val stringUtil: IStringUtil) : IProductHelper {

    override fun getMenuProductPriceString(menuProduct: MenuProduct): String {
        return stringUtil.getCostString(getMenuProductPrice(menuProduct))
    }

    override fun getMenuProductOldPriceString(menuProduct: MenuProduct): String {
        return if (menuProduct.discountCost == null) {
            ""
        } else {
            stringUtil.getCostString(menuProduct.cost)
        }
    }

    override fun getCartProductPriceString(cartProduct: CartProduct): String {
        return stringUtil.getCostString(getCartProductPrice(cartProduct))
    }

    override fun getCartProductOldPriceString(cartProduct: CartProduct): String {
        return if (cartProduct.menuProduct.discountCost == null) {
            ""
        } else {
            stringUtil.getCostString(cartProduct.menuProduct.cost * cartProduct.count)
        }
    }

    override fun getFullPriceString(cartProductList: List<CartProduct>): String {
        return stringUtil.getCostString(getNewTotalCost(cartProductList))
    }

    override fun getFullPriceStringWithDelivery(
        cartProductList: List<CartProduct>,
        delivery: Delivery
    ): String {
        return if (getDifferenceBeforeFreeDelivery(cartProductList, delivery.forFree) > 0) {
            stringUtil.getCostString(getNewTotalCost(cartProductList) + delivery.cost)
        } else {
            getFullPriceString(cartProductList)
        }
    }

    override fun getDifferenceBeforeFreeDeliveryString(
        cartProductList: List<CartProduct>,
        priceForFreeDelivery: Int
    ): String {
        return if (getDifferenceBeforeFreeDelivery(cartProductList, priceForFreeDelivery) > 0) {
            stringUtil.getCostString(priceForFreeDelivery - getNewTotalCost(cartProductList))
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

    override fun getTotalCount(cartProductList: List<CartProduct>): Int {
        return cartProductList.sumOf { cartProduct ->
            cartProduct.count
        }
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