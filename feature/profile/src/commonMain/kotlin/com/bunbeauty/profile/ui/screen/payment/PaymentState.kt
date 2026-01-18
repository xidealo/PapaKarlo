package com.bunbeauty.profile.ui.screen.payment

import com.bunbeauty.core.model.Payment

data class PaymentState(
    val paymentInfo: String,
    val payment: Payment? = null,
)
