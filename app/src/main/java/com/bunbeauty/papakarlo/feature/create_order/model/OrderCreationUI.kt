package com.bunbeauty.papakarlo.feature.create_order.model

data class OrderCreationUI(
    val isDelivery: Boolean,
    val address: String?,
    val comment: String?,
    val deferredTime: String,
    val totalCost: String?,
    val deliveryCost: String?,
    val amountToPay: String?,
    val isLoading: Boolean,
) {
    val switcherPosition = if (isDelivery) {
        0
    } else {
        1
    }
}
