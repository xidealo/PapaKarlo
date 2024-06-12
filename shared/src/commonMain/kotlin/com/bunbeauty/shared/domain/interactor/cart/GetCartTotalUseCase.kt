package com.bunbeauty.shared.domain.interactor.cart

import com.bunbeauty.shared.domain.feature.cart.GetDeliveryCostUseCase
import com.bunbeauty.shared.domain.feature.discount.GetDiscountUseCase
import com.bunbeauty.shared.domain.model.cart.CartTotal
import com.bunbeauty.shared.domain.repo.CartProductRepo

class GetCartTotalUseCase(
    private val cartProductRepo: CartProductRepo,
    private val getDiscountUseCase: GetDiscountUseCase,
    private val getNewTotalCostUseCase: GetNewTotalCostUseCase,
    private val getOldTotalCostUseCase: GetOldTotalCostUseCase,
    private val getDeliveryCostUseCase: GetDeliveryCostUseCase,
) {

    suspend operator fun invoke(isDelivery: Boolean): CartTotal {
        val cartProductList = cartProductRepo.getCartProductList()

        val newTotalCost = getNewTotalCostUseCase(cartProductList)
        val oldTotalCost = checkOldTotalCost(
            oldTotalCost = getOldTotalCostUseCase(cartProductList),
            newTotalCost = newTotalCost
        )
        val deliveryCost = getDeliveryCostUseCase(
            newTotalCost = newTotalCost,
            isDelivery = isDelivery
        )

        return CartTotal(
            discount = getDiscountUseCase()?.firstOrderDiscount,
            deliveryCost = deliveryCost,
            oldTotalCost = oldTotalCost,
            newTotalCost = newTotalCost,
            oldFinalCost = oldTotalCost?.let {
                oldTotalCost + (deliveryCost ?: 0)
            },
            newFinalCost = newTotalCost + (deliveryCost ?: 0),
        )
    }

    private fun checkOldTotalCost(oldTotalCost: Int, newTotalCost: Int): Int? {
        return if (oldTotalCost == newTotalCost) {
            null
        } else {
            oldTotalCost
        }
    }
}