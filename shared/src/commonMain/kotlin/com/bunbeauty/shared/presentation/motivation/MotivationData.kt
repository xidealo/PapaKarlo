package com.bunbeauty.shared.presentation.motivation

sealed interface MotivationData {
    data class MinOrderCost(
        val cost: String,
    ) : MotivationData

    data class ForLowerDelivery(
        val increaseAmountBy: String,
        val progress: Float,
        val isFree: Boolean,
    ) : MotivationData

    data class LowerDeliveryAchieved(
        val isFree: Boolean,
    ) : MotivationData
}
