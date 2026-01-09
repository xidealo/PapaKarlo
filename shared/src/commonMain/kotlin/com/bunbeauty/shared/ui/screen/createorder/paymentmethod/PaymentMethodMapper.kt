package com.bunbeauty.shared.ui.screen.createorder.paymentmethod

import androidx.compose.runtime.Composable
import com.bunbeauty.core.model.payment_method.PaymentMethod
import com.bunbeauty.core.model.payment_method.PaymentMethodName
import com.bunbeauty.core.model.payment_method.SelectablePaymentMethod
import com.bunbeauty.profile.ui.screen.payment.PaymentMethodUI
import com.bunbeauty.profile.ui.screen.payment.PaymentMethodValueUI
import com.bunbeauty.shared.ui.screen.createorder.SelectablePaymentMethodUI
import org.jetbrains.compose.resources.stringResource
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.msg_payment_card
import papakarlo.designsystem.generated.resources.msg_payment_card_number
import papakarlo.designsystem.generated.resources.msg_payment_cash
import papakarlo.designsystem.generated.resources.msg_payment_phone_number

@Composable
fun SelectablePaymentMethod.toSelectablePaymentMethodUI(): SelectablePaymentMethodUI =
    SelectablePaymentMethodUI(
        uuid = paymentMethod.uuid,
        name = paymentMethod.name.toPaymentMethodString(),
        isSelected = isSelected,
    )

@Composable
fun PaymentMethod.toPaymentMethodUI(): PaymentMethodUI =
    PaymentMethodUI(
        uuid = uuid,
        name = name.toPaymentMethodString(),
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

@Composable
private fun PaymentMethodName.toPaymentMethodString(): String =
    when (this) {
        PaymentMethodName.CASH -> Res.string.msg_payment_cash
        PaymentMethodName.CARD -> Res.string.msg_payment_card
        PaymentMethodName.CARD_NUMBER -> Res.string.msg_payment_card_number
        PaymentMethodName.PHONE_NUMBER -> Res.string.msg_payment_phone_number
    }.let { nameResId ->
        stringResource(nameResId)
    }
