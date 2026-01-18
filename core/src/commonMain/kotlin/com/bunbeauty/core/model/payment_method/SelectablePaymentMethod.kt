package com.bunbeauty.core.model.payment_method

data class SelectablePaymentMethod(
    val paymentMethod: PaymentMethod,
    val isSelected: Boolean,
)
