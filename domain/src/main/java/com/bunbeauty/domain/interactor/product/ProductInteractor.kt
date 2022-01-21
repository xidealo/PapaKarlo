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
        val delivery = dataStoreRepo.getDelivery()
        return if (getNewTotalCost(productList) < delivery.forFree) {
            delivery.cost
        } else {
            0
        }
    }

    override fun getOldAmountToPay(productList: List<ProductPosition>): Int? {
        return getOldTotalCost(productList)
    }

    override suspend fun getOldAmountToPayWithDelivery(productList: List<ProductPosition>): Int? {
        val oldAmountToPay = getOldAmountToPay(productList) ?: return null
        return getDeliveryCost(productList) + oldAmountToPay
    }

    override fun getNewAmountToPay(productList: List<ProductPosition>): Int {
        return getNewTotalCost(productList)
    }

    override suspend fun getNewAmountToPayWithDelivery(productList: List<ProductPosition>): Int {
        return getDeliveryCost(productList) + getNewAmountToPay(productList)
    }

    override fun getProductPositionNewCost(productPosition: ProductPosition): Int {
        return productPosition.product.newPrice * productPosition.count
    }

    override fun getProductPositionOldCost(productPosition: ProductPosition): Int? {
        val oldPrice = productPosition.product.oldPrice ?: return null
        return oldPrice * productPosition.count
    }
}