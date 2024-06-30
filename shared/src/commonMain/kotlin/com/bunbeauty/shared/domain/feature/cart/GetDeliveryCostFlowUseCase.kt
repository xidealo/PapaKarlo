package com.bunbeauty.shared.domain.feature.cart

import com.bunbeauty.shared.domain.feature.address.GetCurrentUserAddressFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class GetDeliveryCostFlowUseCase(
    private val getCurrentUserAddressFlowUseCase: GetCurrentUserAddressFlowUseCase
) {

    suspend operator fun invoke(
        newTotalCost: Int,
        isDelivery: Boolean? = null,
    ): Flow<Int?> {
        if (isDelivery == false) {
            return flowOf(null)
        }

        return getCurrentUserAddressFlowUseCase().map { userAddress ->
            val minOrderCost = userAddress?.minOrderCost ?: 0
            if (newTotalCost < minOrderCost) {
                null
            } else {
                val forLowDeliveryCost = userAddress?.forLowDeliveryCost
                val lowDeliveryCost = userAddress?.lowDeliveryCost
                val normalDeliveryCost = userAddress?.normalDeliveryCost

                if (forLowDeliveryCost != null && newTotalCost >= forLowDeliveryCost) {
                    lowDeliveryCost
                } else {
                    normalDeliveryCost
                }
            }
        }
    }
}