package com.bunbeauty.core.motivation


fun MotivationData.toMotivationUi(): MotivationUi =
    when (this) {
        is MotivationData.MinOrderCost -> {
            MotivationUi.MinOrderCost(cost = cost)
        }

        is MotivationData.ForLowerDelivery -> {
            MotivationUi.ForLowerDelivery(
                increaseAmountBy = increaseAmountBy,
                progress = progress,
                isFree = isFree,
            )
        }

        is MotivationData.LowerDeliveryAchieved -> {
            MotivationUi.LowerDeliveryAchieved(isFree = isFree)
        }
    }
