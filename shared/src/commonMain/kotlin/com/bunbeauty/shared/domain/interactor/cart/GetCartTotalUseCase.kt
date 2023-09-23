package com.bunbeauty.shared.domain.interactor.cart

import com.bunbeauty.shared.domain.feature.discount.GetDiscountUseCase
import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.model.cart.CartTotal
import com.bunbeauty.shared.domain.repo.CartProductRepo
import com.bunbeauty.shared.domain.repo.DeliveryRepo

class GetCartTotalUseCase(
    private val cartProductRepo: CartProductRepo,
    private val deliveryRepo: DeliveryRepo,
    private val getDiscountUseCase: GetDiscountUseCase,
    private val getNewTotalCostUseCase: GetNewTotalCostUseCase,
) {

    suspend operator fun invoke(isDelivery: Boolean): CartTotal {
        val cartProductList = cartProductRepo.getCartProductList()

        val newTotalCost = getNewTotalCostUseCase(cartProductList)
        val oldTotalCost = getOldTotalCost(cartProductList)

        val deliveryCost = getDeliveryCost(isDelivery, newTotalCost)

        return CartTotal(
            totalCost = getTotalCost(cartProductList),
            deliveryCost = deliveryCost,
            oldFinalCost = oldTotalCost?.let {
                oldTotalCost + deliveryCost
            },
            newFinalCost = newTotalCost + deliveryCost,
            discount = getDiscountUseCase()?.firstOrderDiscount
        )
    }

    private suspend fun getDeliveryCost(isDelivery: Boolean, newTotalCost: Int): Int {
        val delivery = deliveryRepo.getDelivery() ?: error("Delivery info is not found")
        return if (isDelivery && newTotalCost < delivery.forFree) {
            delivery.cost
        } else {
            0
        }
    }

    private fun getTotalCost(productList: List<CartProduct>): Int {
        return productList.sumOf { orderProductEntity ->
            orderProductEntity.count * orderProductEntity.product.newPrice
        }
    }


    private suspend fun getOldTotalCost(productList: List<CartProduct>): Int? {
        val oldTotalCost = productList.sumOf { orderProductEntity ->
            orderProductEntity.count * (orderProductEntity.product.oldPrice
                ?: orderProductEntity.product.newPrice)
        }

        return if (oldTotalCost == getNewTotalCostUseCase(productList)) {
            null
        } else {
            oldTotalCost
        }
    }
}