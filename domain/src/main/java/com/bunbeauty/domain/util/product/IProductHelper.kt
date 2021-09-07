package com.bunbeauty.domain.util.product

import com.bunbeauty.domain.model.product.MenuProduct
import com.bunbeauty.domain.model.product.ProductPosition

interface IProductHelper {

    fun <T: ProductPosition> getNewTotalCost(productList: List<T>): Int
    fun <T: ProductPosition> getOldTotalCost(productList: List<T>): Int?

    fun getCartProductNewCost(product: ProductPosition): Int
    fun getCartProductOldCost(product: ProductPosition): Int?

    fun getMenuProductNewPrice(menuProduct: MenuProduct): Int
    fun getMenuProductOldPrice(menuProduct: MenuProduct): Int?

    fun <T: ProductPosition> getTotalCount(productList: List<T>): Int
}