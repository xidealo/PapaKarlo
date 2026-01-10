package com.bunbeauty.profile.ui.screen.payment

import com.bunbeauty.designsystem.model.PaymentMethodUI

data class SelectablePaymentMethodUI(
    val paymentMethod: PaymentMethodUI,
    val isSelected: Boolean,
)
