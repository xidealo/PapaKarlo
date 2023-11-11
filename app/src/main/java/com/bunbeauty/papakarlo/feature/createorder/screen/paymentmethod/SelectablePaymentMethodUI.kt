package com.bunbeauty.papakarlo.feature.createorder.screen.paymentmethod

import android.os.Parcelable
import com.bunbeauty.papakarlo.feature.profile.screen.payment.PaymentMethodUI
import kotlinx.parcelize.Parcelize

@Parcelize
class SelectablePaymentMethodUI(
    val paymentMethodUI: PaymentMethodUI,
    val isSelected: Boolean
): Parcelable
