package com.bunbeauty.domain.util.product

import com.bunbeauty.domain.model.product.ProductPosition

interface IProductHelper {

    fun <T : ProductPosition> getNewTotalCost(productList: List<T>): Int
    fun <T : ProductPosition> getOldTotalCost(productList: List<T>): Int?

    fun getProductPositionNewCost(product: ProductPosition): Int
    fun getProductPositionOldCost(product: ProductPosition): Int?

    fun <T : ProductPosition> getTotalCount(productList: List<T>): Int
}