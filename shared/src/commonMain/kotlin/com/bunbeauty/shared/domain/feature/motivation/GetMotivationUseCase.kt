package com.bunbeauty.shared.domain.feature.motivation

import com.bunbeauty.shared.domain.feature.address.GetCurrentUserAddressUseCase
import com.bunbeauty.shared.domain.use_case.address.GetUserAddressListUseCase

class GetMotivationUseCase(
    private val getCurrentUserAddressUseCase: GetCurrentUserAddressUseCase,
    private val getUserAddressListUseCase: GetUserAddressListUseCase,
) {
    suspend operator fun invoke(
        newTotalCost: Int,
        isDelivery: Boolean,
    ): Motivation? {
        if (!isDelivery) {
            return null
        }

        getUserAddressListUseCase()
        val currentUserAddress = getCurrentUserAddressUseCase()

        val minOrderCost = currentUserAddress?.minOrderCost
        val forLowDeliveryCost = currentUserAddress?.forLowDeliveryCost
        val lowDeliveryCost = currentUserAddress?.lowDeliveryCost

        return when {
            (minOrderCost != null) && (newTotalCost < minOrderCost) -> {
                Motivation.MinOrderCost(cost = minOrderCost)
            }
            (forLowDeliveryCost != null) && (lowDeliveryCost != null) -> {
                if (newTotalCost >= forLowDeliveryCost) {
                    Motivation.LowerDeliveryAchieved(isFree = lowDeliveryCost <= 0)
                } else {
                    val increaseAmountBy = forLowDeliveryCost - newTotalCost
                    Motivation.ForLowerDelivery(
                        increaseAmountBy = increaseAmountBy,
                        progress =
                            if (newTotalCost >= forLowDeliveryCost) {
                                1f
                            } else {
                                newTotalCost.toFloat() / forLowDeliveryCost
                            },
                        isFree = lowDeliveryCost <= 0,
                    )
                }
            }
            else -> null
        }
    }
}
