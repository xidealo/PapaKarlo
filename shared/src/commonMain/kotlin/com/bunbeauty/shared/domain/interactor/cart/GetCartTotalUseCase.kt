package com.bunbeauty.shared.domain.interactor.cart

import com.bunbeauty.shared.domain.feature.discount.GetDiscountUseCase
import com.bunbeauty.shared.domain.model.cart.CartTotal
import com.bunbeauty.shared.domain.repo.CartProductRepo
import com.bunbeauty.shared.domain.repo.DeliveryRepo

class GetCartTotalUseCase(
    private val cartProductRepo: CartProductRepo,
    private val deliveryRepo: DeliveryRepo,
    private val getDiscountUseCase: GetDiscountUseCase,
    private val getNewTotalCostUseCase: GetNewTotalCostUseCase,
    private val getOldTotalCostUseCase: GetOldTotalCostUseCase,
) {

    suspend operator fun invoke(isDelivery: Boolean): CartTotal {
        val cartProductList = cartProductRepo.getCartProductList()

        val newTotalCost = getNewTotalCostUseCase(cartProductList)
        val oldTotalCost = checkOldTotalCost(
            oldTotalCost = getOldTotalCostUseCase(cartProductList),
            newTotalCost = newTotalCost
        )

        val deliveryCost = getDeliveryCost(isDelivery, newTotalCost)

        return CartTotal(
            totalCost = newTotalCost,
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

    private fun checkOldTotalCost(oldTotalCost: Int, newTotalCost: Int): Int? {
        return if (oldTotalCost == newTotalCost) {
            null
        } else {
            oldTotalCost
        }
    }
}