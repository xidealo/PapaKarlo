package com.bunbeauty.shared.presentation.consumercart.mapper

import com.bunbeauty.shared.Constants.RUBLE_CURRENCY
import com.bunbeauty.shared.domain.feature.cart.model.Warning
import com.bunbeauty.shared.presentation.consumercart.ConsumerCart

fun Warning.toWarningItem(): ConsumerCart.DataState.WarningItem {
    return  when (this) {
        is Warning.MinOrderCost -> {
            ConsumerCart.DataState.WarningItem.MinOrderCost(
                cost = "$cost $RUBLE_CURRENCY"
            )
        }

        is Warning.ForLowerDelivery -> {
            ConsumerCart.DataState.WarningItem.ForLowerDelivery(
                increaseAmountBy = "$increaseAmountBy $RUBLE_CURRENCY"
            )
        }

        is Warning.ForFreeDelivery -> {
            ConsumerCart.DataState.WarningItem.ForFreeDelivery(
                increaseAmountBy = "$increaseAmountBy $RUBLE_CURRENCY"
            )
        }
    }
}