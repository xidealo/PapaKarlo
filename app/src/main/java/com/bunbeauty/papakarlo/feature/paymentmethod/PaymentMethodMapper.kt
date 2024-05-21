package com.bunbeauty.papakarlo.feature.paymentmethod

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.feature.profile.screen.payment.PaymentMethodUI
import com.bunbeauty.papakarlo.feature.profile.screen.payment.PaymentMethodValueUI
import com.bunbeauty.shared.domain.model.payment_method.PaymentMethod
import com.bunbeauty.shared.domain.model.payment_method.PaymentMethodName

@Composable
fun PaymentMethod.toPaymentMethodUI(): PaymentMethodUI {
    return PaymentMethodUI(
        uuid = uuid,
        name = name.toPaymentMethodString(),
        value = valueToShow?.let { value ->
            valueToCopy?.let { valueToCopy ->
                PaymentMethodValueUI(
                    value = value,
                    valueToCopy = valueToCopy
                )
            }
        }
    )
}

@Composable
private fun PaymentMethodName.toPaymentMethodString(): String {
    return when (this) {
        PaymentMethodName.CASH -> R.string.msg_payment_cash
        PaymentMethodName.CARD -> R.string.msg_payment_card
        PaymentMethodName.CARD_NUMBER -> R.string.msg_payment_card_number
        PaymentMethodName.PHONE_NUMBER -> R.string.msg_payment_phone_number
    }.let { nameResId ->
        stringResource(nameResId)
    }
}