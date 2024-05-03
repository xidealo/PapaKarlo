package com.bunbeauty.shared.domain.feature.cart

import com.bunbeauty.shared.domain.feature.address.GetCurrentUserAddressUseCase
import com.bunbeauty.shared.domain.feature.cart.model.Motivation
import com.bunbeauty.shared.domain.model.cart.ConsumerCartDomain

class GetConsumerCartWarningUseCase(
    private val getCurrentUserAddressUseCase: GetCurrentUserAddressUseCase
) {

    suspend operator fun invoke(consumerCartDomain: ConsumerCartDomain.WithProducts): Motivation? {
        val currentUserAddress = getCurrentUserAddressUseCase()

        val minOrderCost = currentUserAddress?.minOrderCost
        val forLowDeliveryCost = currentUserAddress?.forLowDeliveryCost
        val lowDeliveryCost = currentUserAddress?.lowDeliveryCost

        return if (minOrderCost != null && consumerCartDomain.newTotalCost < minOrderCost) {
            Motivation.MinOrderCost(cost = minOrderCost)
        } else if (
            (forLowDeliveryCost != null)
            && (lowDeliveryCost != null)
        ) {
            if (consumerCartDomain.newTotalCost >= forLowDeliveryCost) {
                Motivation.LowerDeliveryAchieved(isFree = lowDeliveryCost <= 0)
            } else {
                val increaseAmountBy = forLowDeliveryCost - consumerCartDomain.newTotalCost
                Motivation.ForLowerDelivery(
                    increaseAmountBy = increaseAmountBy,
                    progress = if (consumerCartDomain.newTotalCost >= forLowDeliveryCost) {
                        1f
                    } else {
                        consumerCartDomain.newTotalCost.toFloat() / forLowDeliveryCost
                    },
                    isFree = lowDeliveryCost <= 0
                )
            }
        } else {
            null
        }
    }

}