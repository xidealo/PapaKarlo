package com.bunbeauty.papakarlo.feature.profile.screen.payment

import com.bunbeauty.shared.domain.model.Payment

data class PaymentState(
    val paymentInfo: String,
    val payment: Payment? = null
)
