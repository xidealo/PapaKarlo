package com.bunbeauty.domain.util.product

import com.bunbeauty.domain.model.product.Product
import com.bunbeauty.domain.model.product.ProductPosition
import javax.inject.Inject

class ProductHelper @Inject constructor() : IProductHelper {

    override fun <T : ProductPosition> getNewTotalCost(productList: List<T>): Int {
        return productList.sumOf(::getProductPositionNewCost)
    }

    override fun <T : ProductPosition> getOldTotalCost(productList: List<T>): Int? {
        val hasSomeDiscounts = productList.any { cartProduct ->
            cartProduct.menuProduct.discountCost != null
        }

        return if (hasSomeDiscounts) {
            productList.sumOf { cartProduct ->
                getProductPositionOldCost(cartProduct) ?: getProductPositionNewCost(cartProduct)
            }
        } else {
            null
        }
    }

    override fun getProductPositionNewCost(product: ProductPosition): Int {
        return getProductNewPrice(product.menuProduct) * product.count
    }

    override fun getProductPositionOldCost(product: ProductPosition): Int? {
        return getProductOldPrice(product.menuProduct)?.let { oldPrice ->
            oldPrice * product.count
        }
    }

    override fun getProductNewPrice(product: Product): Int {
        return product.discountCost ?: product.cost
    }

    override fun getProductOldPrice(product: Product): Int? {
        return if (product.discountCost == null) {
            null
        } else {
            product.cost
        }
    }

    override fun <T : ProductPosition> getTotalCount(productList: List<T>): Int {
        return productList.sumOf { product ->
            product.count
        }
    }
}