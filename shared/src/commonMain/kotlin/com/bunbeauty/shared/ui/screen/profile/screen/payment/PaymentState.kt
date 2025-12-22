package com.bunbeauty.shared.ui.screen.profile.screen.payment

import com.bunbeauty.shared.domain.model.Payment

data class PaymentState(
    val paymentInfo: String,
    val payment: Payment? = null,
)
