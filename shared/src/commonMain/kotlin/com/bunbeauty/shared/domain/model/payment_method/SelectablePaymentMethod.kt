package com.bunbeauty.shared.domain.model.payment_method

data class SelectablePaymentMethod(
    val paymentMethod: PaymentMethod,
    val isSelected: Boolean,
)