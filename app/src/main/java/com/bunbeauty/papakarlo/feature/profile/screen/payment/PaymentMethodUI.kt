package com.bunbeauty.papakarlo.feature.profile.screen.payment

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaymentMethodUI(
    val uuid: String,
    val name: String,
    val value: PaymentMethodValueUI?,
) : Parcelable

@Parcelize
data class PaymentMethodValueUI(
    val value: String,
    val valueToCopy: String,
) : Parcelable
