package com.bunbeauty.papakarlo.feature.create_order.screen.create_order

import com.bunbeauty.papakarlo.feature.profile.screen.payment.PaymentMethodUI

data class CreateOrderUi(
    val isDelivery: Boolean,
    val deliveryAddress: String?,
    val pickupAddress: String?,
    val comment: String?,
    val deferredTime: String,
    val totalCost: String?,
    val deliveryCost: String?,
    val finalCost: String?,
    val isAddressErrorShown: Boolean,
    val isLoading: Boolean,
    val selectedPaymentMethod: PaymentMethodUI?
) {
    val switcherPosition = if (isDelivery) {
        0
    } else {
        1
    }
}
