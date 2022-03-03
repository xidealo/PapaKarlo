package com.bunbeauty.domain.model.order

data class OrderWithAmounts(
    val order: Order,
    val oldAmountToPay: Int?,
    val newAmountToPay: Int,
)
