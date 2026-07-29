package com.bunbeauty.core.domain.cart

import com.bunbeauty.core.domain.GetNewTotalCostUseCase
import com.bunbeauty.core.domain.discount.GetDiscountUseCase
import com.bunbeauty.core.domain.repo.CartProductRepo
import com.bunbeauty.core.model.cart.CartProduct
import com.bunbeauty.core.model.cart.CartTotal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCartTotalFlowUseCase(
    private val cartProductRepo: CartProductRepo,
    private val getDiscountUseCase: GetDiscountUseCase,
    private val getNewTotalCostUseCase: GetNewTotalCostUseCase,
    private val getOldTotalCostUseCase: GetOldTotalCostUseCase,
    private val getDeliveryCostFlowUseCase: GetDeliveryCostFlowUseCase,
) {
    suspend operator fun invoke(
        isDelivery: Boolean,
        cartProductList: List<CartProduct>? = null,
    ): Flow<CartTotal> {
        val products = cartProductList ?: cartProductRepo.getCartProductList()

        val newTotalCost = getNewTotalCostUseCase(products)
        val oldTotalCost =
            checkOldTotalCost(
                oldTotalCost = getOldTotalCostUseCase(products),
                newTotalCost = newTotalCost,
            )

        return getDeliveryCostFlowUseCase(
            newTotalCost = newTotalCost,
            isDelivery = isDelivery,
        ).map { deliveryCost ->
            CartTotal(
                discount = getDiscountUseCase()?.firstOrderDiscount,
                deliveryCost = deliveryCost,
                oldTotalCost = oldTotalCost,
                newTotalCost = newTotalCost,
                oldFinalCost =
                    oldTotalCost?.let {
                        oldTotalCost + (deliveryCost ?: 0)
                    },
                newFinalCost = newTotalCost + (deliveryCost ?: 0),
            )
        }
    }

    private fun checkOldTotalCost(
        oldTotalCost: Int,
        newTotalCost: Int,
    ): Int? =
        if (oldTotalCost == newTotalCost) {
            null
        } else {
            oldTotalCost
        }
}
