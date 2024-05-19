package com.bunbeauty.shared.domain.feature.cart

import com.bunbeauty.shared.domain.feature.address.GetCurrentUserAddressUseCase

class GetDeliveryCostUseCase(
    private val getCurrentUserAddressUseCase: GetCurrentUserAddressUseCase
) {

    suspend operator fun invoke(
        newTotalCost: Int,
        isDelivery: Boolean? = null,
    ): Int? {
        if (isDelivery == false) {
            return null
        }

        val currentUserAddress = getCurrentUserAddressUseCase()

        val minOrderCost = currentUserAddress?.minOrderCost ?: 0
        if (newTotalCost < minOrderCost) {
            return null
        }

        val forLowDeliveryCost = currentUserAddress?.forLowDeliveryCost
        val lowDeliveryCost = currentUserAddress?.lowDeliveryCost
        val normalDeliveryCost = currentUserAddress?.normalDeliveryCost

        return if (forLowDeliveryCost != null && newTotalCost >= forLowDeliveryCost) {
            lowDeliveryCost
        } else {
            normalDeliveryCost
        }
    }
}