package com.bunbeauty.shared.presentation.consumercart.mapper

import com.bunbeauty.shared.Constants.RUBLE_CURRENCY
import com.bunbeauty.shared.domain.feature.cart.model.Motivation
import com.bunbeauty.shared.presentation.consumercart.ConsumerCart

fun Motivation.toWarningItem(): ConsumerCart.DataState.Motivation {
    return when (this) {
        is Motivation.MinOrderCost -> {
            ConsumerCart.DataState.Motivation.MinOrderCost(
                cost = "$cost $RUBLE_CURRENCY"
            )
        }

        is Motivation.ForLowerDelivery -> {
            ConsumerCart.DataState.Motivation.ForLowerDelivery(
                increaseAmountBy = "$increaseAmountBy $RUBLE_CURRENCY",
                progress = progress,
                isFree = isFree,
            )
        }

        is Motivation.LowerDeliveryAchieved -> {
            ConsumerCart.DataState.Motivation.LowerDeliveryAchieved(isFree = isFree)
        }
    }
}