package com.bunbeauty.domain.util.product

import com.bunbeauty.domain.model.product.MenuProduct
import com.bunbeauty.domain.model.product.ProductPosition
import javax.inject.Inject

class ProductHelper @Inject constructor() : IProductHelper {

    override fun <T: ProductPosition>  getNewTotalCost(productList: List<T>): Int {
        return productList.sumOf(::getCartProductNewCost)
    }

    override fun <T: ProductPosition> getOldTotalCost(productList: List<T>): Int? {
        val hasSomeDiscounts = productList.any { cartProduct ->
            cartProduct.menuProduct.discountCost != null
        }

        return if (hasSomeDiscounts) {
            productList.sumOf { cartProduct ->
                getCartProductOldCost(cartProduct) ?: getCartProductNewCost(cartProduct)
            }
        } else {
            null
        }
    }

    override fun getCartProductNewCost(product: ProductPosition): Int {
        return getMenuProductNewPrice(product.menuProduct) * product.count
    }

    override fun getCartProductOldCost(product: ProductPosition): Int? {
        return getMenuProductOldPrice(product.menuProduct)?.let { oldPrice ->
            oldPrice * product.count
        }
    }

    override fun getMenuProductNewPrice(menuProduct: MenuProduct): Int {
        return menuProduct.discountCost ?: menuProduct.cost
    }

    override fun getMenuProductOldPrice(menuProduct: MenuProduct): Int? {
        return if (menuProduct.discountCost == null) {
            null
        } else {
            menuProduct.cost
        }
    }

    override fun <T: ProductPosition> getTotalCount(productList: List<T>): Int {
        return productList.sumOf { product ->
            product.count
        }
    }
}