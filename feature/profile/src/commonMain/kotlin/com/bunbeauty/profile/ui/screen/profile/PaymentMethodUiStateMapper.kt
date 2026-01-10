package com.bunbeauty.profile.ui.screen.profile

import androidx.compose.runtime.Composable
import com.bunbeauty.core.extension.mapToString
import com.bunbeauty.core.model.payment_method.PaymentMethod
import com.bunbeauty.designsystem.model.PaymentMethodUI
import com.bunbeauty.designsystem.model.PaymentMethodValueUI

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
