package com.bunbeauty.shared.domain.feature.cart.model

sealed interface Motivation {
    data class MinOrderCost(val cost: Int) : Motivation

    data class ForLowerDelivery(
        val increaseAmountBy: Int,
        val progress: Float,
        val isFree: Boolean,
    ) : Motivation

    data class LowerDeliveryAchieved(val isFree: Boolean) : Motivation
}