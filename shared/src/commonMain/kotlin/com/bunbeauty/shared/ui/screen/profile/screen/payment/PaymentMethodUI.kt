package com.bunbeauty.shared.ui.screen.profile.screen.payment

data class PaymentMethodUI(
    val uuid: String,
    val name: String,
    val value: PaymentMethodValueUI?,
)

data class PaymentMethodValueUI(
    val value: String,
    val valueToCopy: String,
)
