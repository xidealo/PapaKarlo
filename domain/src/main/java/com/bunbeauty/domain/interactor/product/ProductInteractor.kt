package com.bunbeauty.domain.interactor.product

import com.bunbeauty.domain.model.product.ProductPosition
import com.bunbeauty.domain.repo.DataStoreRepo
import javax.inject.Inject

class ProductInteractor @Inject constructor(private val dataStoreRepo: DataStoreRepo) :
    IProductInteractor {

    override fun getNewTotalCost(productList: List<ProductPosition>): Int {
        return productList.sumOf(::getProductPositionNewCost)
    }

    override fun getOldTotalCost(productList: List<ProductPosition>): Int? {
        val oldCost = productList.sumOf(::getPositionOldCost)
        return if (oldCost == getNewTotalCost(productList)) {
            null
        } else {
            oldCost
        }
    }

    override suspend fun getDeliveryCost(productList: List<ProductPosition>): Int {
        val delivery = dataStoreRepo.getDelivery()
        return if (getNewTotalCost(productList) < delivery.forFree) {
            delivery.cost
        } else {
            0
        }
    }

    fun getProductPositionNewCost(productPosition: ProductPosition): Int {
        return productPosition.product.newPrice * productPosition.count
    }

    fun getPositionOldCost(productPosition: ProductPosition): Int {
        return (productPosition.product.oldPrice ?: productPosition.product.newPrice) *
                productPosition.count
    }
}