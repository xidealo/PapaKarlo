package com.bunbeauty.core.motivation

import com.bunbeauty.core.Constants.RUBLE_CURRENCY
import com.bunbeauty.core.domain.motivation.Motivation


fun Motivation.toMotivationData(): MotivationData =
    when (this) {
        is Motivation.MinOrderCost -> {
            MotivationData.MinOrderCost(
                cost = "$cost $RUBLE_CURRENCY",
            )
        }

        is Motivation.ForLowerDelivery -> {
            MotivationData.ForLowerDelivery(
                increaseAmountBy = "$increaseAmountBy $RUBLE_CURRENCY",
                progress = progress,
                isFree = isFree,
            )
        }

        is Motivation.LowerDeliveryAchieved -> {
            MotivationData.LowerDeliveryAchieved(isFree = isFree)
        }
    }
