package com.bunbeauty.domain.interactor.product

import com.bunbeauty.domain.model.product.ProductPosition
import com.bunbeauty.domain.repo.DataStoreRepo

class ProductInteractor(
    private val dataStoreRepo: DataStoreRepo
) : IProductInteractor {

    override fun getNewTotalCost(productList: List<ProductPosition>): Int {
        return productList.sumOf(::getProductPositionNewCost)
    }

    override fun getOldTotalCost(productList: List<ProductPosition>): Int? {
        val oldCost = productList.sumOf { productPosition ->
            getProductPositionOldCost(productPosition) ?: getProductPositionNewCost(productPosition)
        }
        return if (oldCost == getNewTotalCost(productList)) {
            null
        } else {
            oldCost
        }
    }

    override suspend fun getDeliveryCost(productList: List<ProductPosition>): Int {
        val delivery = dataStoreRepo.getDelivery() ?: return 0
        return if (getNewTotalCost(productList) < delivery.forFree) {
            delivery.cost
        } else {
            0
        }
    }

    override fun getOldAmountToPay(productList: List<ProductPosition>, deliveryCost: Int?): Int? {
        val oldAmountToPay = getOldTotalCost(productList) ?: return null
        return (deliveryCost ?: 0) + oldAmountToPay
    }

    override fun getNewAmountToPay(productList: List<ProductPosition>, deliveryCost: Int?): Int {
        return (deliveryCost ?: 0) + getNewTotalCost(productList)
    }

    override fun getProductPositionNewCost(productPosition: ProductPosition): Int {
        return productPosition.product.newPrice * productPosition.count
    }

    override fun getProductPositionOldCost(productPosition: ProductPosition): Int? {
        val oldPrice = productPosition.product.oldPrice ?: return null
        return oldPrice * productPosition.count
    }
}