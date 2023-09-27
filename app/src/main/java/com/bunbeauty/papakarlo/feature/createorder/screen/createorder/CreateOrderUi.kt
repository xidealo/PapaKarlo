package com.bunbeauty.papakarlo.feature.createorder.screen.createorder

import com.bunbeauty.papakarlo.feature.profile.screen.payment.PaymentMethodUI

data class CreateOrderUi(
    val isDelivery: Boolean,
    val deliveryAddress: String?,
    val pickupAddress: String?,
    val comment: String?,
    val deferredTime: String,
    val totalCost: String?,
    val deliveryCost: String?,
    val oldFinalCost: String?,
    val newFinalCost: String?,
    val isAddressErrorShown: Boolean,
    val isLoading: Boolean,
    val discount: String?,
    val selectedPaymentMethod: PaymentMethodUI?
) {
    val switcherPosition = if (isDelivery) {
        0
    } else {
        1
    }
}
