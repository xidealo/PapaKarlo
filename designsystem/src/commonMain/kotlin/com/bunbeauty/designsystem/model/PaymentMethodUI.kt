package com.bunbeauty.designsystem.model

data class PaymentMethodUI(
    val uuid: String,
    val name: String,
    val value: PaymentMethodValueUI?,
)

data class PaymentMethodValueUI(
    val value: String,
    val valueToCopy: String,
)
