package com.bunbeauty.shared.domain.interactor.product

import com.bunbeauty.shared.domain.model.product.ProductPosition

class ProductInteractor : IProductInteractor {

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

    override fun getProductPositionNewCost(productPosition: ProductPosition): Int {
        return productPosition.product.newPrice * productPosition.count
    }

    override fun getProductPositionOldCost(productPosition: ProductPosition): Int? {
        val oldPrice = productPosition.product.oldPrice ?: return null
        return oldPrice * productPosition.count
    }
}