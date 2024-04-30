package com.bunbeauty.shared.domain.feature.cart

import com.bunbeauty.shared.domain.feature.address.GetCurrentUserAddressUseCase
import com.bunbeauty.shared.domain.feature.cart.model.Warning
import com.bunbeauty.shared.domain.model.cart.ConsumerCartDomain

class GetConsumerCartWarningUseCase(
    private val getCurrentUserAddressUseCase: GetCurrentUserAddressUseCase
) {

    suspend operator fun invoke(consumerCartDomain: ConsumerCartDomain.WithProducts): Warning? {
        val currentUserAddress = getCurrentUserAddressUseCase()

        val minOrderCost = currentUserAddress?.minOrderCost
        val forLowDeliveryCost = currentUserAddress?.forLowDeliveryCost
        val lowDeliveryCost = currentUserAddress?.lowDeliveryCost

        return if (minOrderCost != null && consumerCartDomain.newTotalCost < minOrderCost) {
            Warning.MinOrderCost(cost = minOrderCost)
        } else if (
            (forLowDeliveryCost != null)
            && (lowDeliveryCost != null)
            && (consumerCartDomain.newTotalCost < forLowDeliveryCost)
        ) {
            val increaseAmountBy = consumerCartDomain.newTotalCost - forLowDeliveryCost
            if (lowDeliveryCost > 0) {
                Warning.ForLowerDelivery(increaseAmountBy = increaseAmountBy)
            } else {
                Warning.ForFreeDelivery(increaseAmountBy = increaseAmountBy)
            }
        } else {
            null
        }
    }

}