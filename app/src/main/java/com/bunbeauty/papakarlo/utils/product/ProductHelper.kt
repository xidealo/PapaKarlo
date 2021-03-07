package com.bunbeauty.papakarlo.utils.product

import android.util.Log
import com.bunbeauty.papakarlo.data.model.CartProduct
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
        return stringHelper.toStringPrice(cartProductList.map { cartProduct ->
            getCartProductPrice(cartProduct)
        }.sum())
    }

    private fun getMenuProductPrice(menuProduct: MenuProduct): Int {
        return menuProduct.discountCost ?: menuProduct.cost
    }

    private fun getCartProductPrice(cartProduct: CartProduct): Int {
        return getMenuProductPrice(cartProduct.menuProduct) * cartProduct.count
    }


}