package com.bunbeauty.shared.domain.feature.cart.model

sealed interface Warning {
    data class MinOrderCost(val cost: Int) : Warning
    data class ForFreeDelivery(val increaseAmountBy: Int) : Warning
    data class ForLowerDelivery(val increaseAmountBy: Int) : Warning
}