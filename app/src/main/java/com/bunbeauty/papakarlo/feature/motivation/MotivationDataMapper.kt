package com.bunbeauty.papakarlo.feature.motivation

import com.bunbeauty.shared.presentation.motivation.MotivationData

fun MotivationData.toMotivationUi(): MotivationUi {
    return when (this) {
        is MotivationData.MinOrderCost -> {
            MotivationUi.MinOrderCost(cost = cost)
        }

        is MotivationData.ForLowerDelivery -> {
            MotivationUi.ForLowerDelivery(
                increaseAmountBy = increaseAmountBy,
                progress = progress,
                isFree = isFree
            )
        }

        is MotivationData.LowerDeliveryAchieved -> {
            MotivationUi.LowerDeliveryAchieved(isFree = isFree)
        }
    }
}