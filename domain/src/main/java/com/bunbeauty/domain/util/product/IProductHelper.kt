package com.bunbeauty.domain.util.product

import com.bunbeauty.domain.model.product.Product
import com.bunbeauty.domain.model.product.ProductPosition

interface IProductHelper {

    fun <T : ProductPosition> getNewTotalCost(productList: List<T>): Int
    fun <T : ProductPosition> getOldTotalCost(productList: List<T>): Int?

    fun getProductPositionNewCost(product: ProductPosition): Int
    fun getProductPositionOldCost(product: ProductPosition): Int?

    fun getProductNewPrice(product: Product): Int
    fun getProductOldPrice(product: Product): Int?

    fun <T : ProductPosition> getTotalCount(productList: List<T>): Int
}