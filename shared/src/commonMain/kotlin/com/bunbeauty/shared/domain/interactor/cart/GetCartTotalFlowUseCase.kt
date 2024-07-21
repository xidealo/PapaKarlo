package com.bunbeauty.shared.domain.interactor.cart

import com.bunbeauty.shared.domain.feature.cart.GetDeliveryCostFlowUseCase
import com.bunbeauty.shared.domain.feature.discount.GetDiscountUseCase
import com.bunbeauty.shared.domain.model.cart.CartTotal
import com.bunbeauty.shared.domain.repo.CartProductRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCartTotalFlowUseCase(
    private val cartProductRepo: CartProductRepo,
    private val getDiscountUseCase: GetDiscountUseCase,
    private val getNewTotalCostUseCase: GetNewTotalCostUseCase,
    private val getOldTotalCostUseCase: GetOldTotalCostUseCase,
    private val getDeliveryCostFlowUseCase: GetDeliveryCostFlowUseCase
) {

    suspend operator fun invoke(isDelivery: Boolean): Flow<CartTotal> {
        val cartProductList = cartProductRepo.getCartProductList()

        val newTotalCost = getNewTotalCostUseCase(cartProductList)
        val oldTotalCost = checkOldTotalCost(
            oldTotalCost = getOldTotalCostUseCase(cartProductList),
            newTotalCost = newTotalCost
        )

        return getDeliveryCostFlowUseCase(
            newTotalCost = newTotalCost,
            isDelivery = isDelivery
        ).map { deliveryCost ->
            CartTotal(
                discount = getDiscountUseCase()?.firstOrderDiscount,
                deliveryCost = deliveryCost,
                oldTotalCost = oldTotalCost,
                newTotalCost = newTotalCost,
                oldFinalCost = oldTotalCost?.let {
                    oldTotalCost + (deliveryCost ?: 0)
                },
                newFinalCost = newTotalCost + (deliveryCost ?: 0)
            )
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
