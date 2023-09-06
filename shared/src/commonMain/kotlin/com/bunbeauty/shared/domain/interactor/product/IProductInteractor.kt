package com.bunbeauty.shared.domain.interactor.product

import com.bunbeauty.shared.domain.model.product.ProductPosition

interface IProductInteractor {

    @Deprecated("but using on ios")
    fun getNewTotalCost(productList: List<ProductPosition>): Int

    fun getOldTotalCost(productList: List<ProductPosition>): Int?
    fun getProductPositionNewCost(productPosition: ProductPosition): Int
    fun getProductPositionOldCost(productPosition: ProductPosition): Int?
}