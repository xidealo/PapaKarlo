package com.bunbeauty.papakarlo.feature.profile.screen.payment

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class PaymentMethodsArgument(
    val paymentMethodList: List<PaymentMethodUI>,
) : Parcelable
