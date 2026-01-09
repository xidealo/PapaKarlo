package com.bunbeauty.shared.ui.screen.profile.screen.payment

import com.bunbeauty.core.model.Payment

data class PaymentState(
    val paymentInfo: String,
    val payment: Payment? = null,
)
