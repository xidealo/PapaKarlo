package com.bunbeauty.profile.ui.screen.profile

import androidx.compose.runtime.Composable
import com.bunbeauty.core.extension.mapToString
import com.bunbeauty.core.model.payment_method.PaymentMethod
import com.bunbeauty.profile.ui.screen.payment.PaymentMethodUI
import com.bunbeauty.profile.ui.screen.payment.PaymentMethodValueUI

@Composable
fun PaymentMethod.map(): PaymentMethodUI =
    PaymentMethodUI(
        uuid = uuid,
        name = name.mapToString(),
        value =
            valueToShow?.let { value ->
                valueToCopy?.let { valueToCopy ->
                    PaymentMethodValueUI(
                        value = value,
                        valueToCopy = valueToCopy,
                    )
                }
            },
    )
