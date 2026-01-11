package com.bunbeauty.core.extension

import androidx.compose.runtime.Composable
import com.bunbeauty.core.model.payment_method.PaymentMethodName
import org.jetbrains.compose.resources.stringResource
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.msg_payment_card
import papakarlo.designsystem.generated.resources.msg_payment_card_number
import papakarlo.designsystem.generated.resources.msg_payment_cash
import papakarlo.designsystem.generated.resources.msg_payment_phone_number

@Composable
fun PaymentMethodName.mapToString(): String =
    when (this) {
        PaymentMethodName.CASH -> Res.string.msg_payment_cash
        PaymentMethodName.CARD -> Res.string.msg_payment_card
        PaymentMethodName.CARD_NUMBER -> Res.string.msg_payment_card_number
        PaymentMethodName.PHONE_NUMBER -> Res.string.msg_payment_phone_number
    }.let { nameResId ->
        stringResource(resource = nameResId)
    }
