package com.bunbeauty.shared.ui.motivation

import androidx.compose.runtime.Immutable

@Immutable
sealed interface MotivationUi {
    @Immutable
    data class MinOrderCost(
        val cost: String,
    ) : MotivationUi

    @Immutable
    data class ForLowerDelivery(
        val increaseAmountBy: String,
        val progress: Float,
        val isFree: Boolean,
    ) : MotivationUi

    @Immutable
    data class LowerDeliveryAchieved(
        val isFree: Boolean,
    ) : MotivationUi
}
