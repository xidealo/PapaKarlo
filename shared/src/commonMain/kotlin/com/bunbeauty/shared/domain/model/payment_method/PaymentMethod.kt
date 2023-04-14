package com.bunbeauty.shared.domain.model.payment_method

data class PaymentMethod(
    val uuid: String,
    val name: PaymentMethodName,
    val value: String?,
    val valueToCopy: String?
)