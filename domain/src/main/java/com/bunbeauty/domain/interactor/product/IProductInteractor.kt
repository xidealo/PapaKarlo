package com.bunbeauty.domain.interactor.product

import com.bunbeauty.domain.model.product.ProductPosition

interface IProductInteractor {

    fun getNewTotalCost(productList: List<ProductPosition>): Int
    fun getOldTotalCost(productList: List<ProductPosition>): Int?
    suspend fun getDeliveryCost(productList: List<ProductPosition>): Int
}