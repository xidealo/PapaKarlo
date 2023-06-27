package com.bunbeauty.papakarlo.feature.profile.screen.profile

import android.content.res.Resources
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.feature.profile.screen.payment.PaymentMethodUI
import com.bunbeauty.papakarlo.feature.profile.screen.payment.PaymentMethodValueUI
import com.bunbeauty.shared.domain.model.payment_method.PaymentMethod
import com.bunbeauty.shared.domain.model.payment_method.PaymentMethodName

class PaymentMethodUiStateMapper(
    private val resources: Resources,
) {

    fun map(paymentMethodList: List<PaymentMethod>): List<PaymentMethodUI> {
        return paymentMethodList.map { paymentMethod ->
            PaymentMethodUI(
                uuid = paymentMethod.uuid,
                name = when (paymentMethod.name) {
                    PaymentMethodName.CASH -> R.string.msg_payment_cash
                    PaymentMethodName.CARD -> R.string.msg_payment_card
                    PaymentMethodName.CARD_NUMBER -> R.string.msg_payment_card_number
                    PaymentMethodName.PHONE_NUMBER -> R.string.msg_payment_phone_number
                }.let { nameResId ->
                    resources.getString(nameResId)
                },
                value = paymentMethod.valueToShow?.let { value ->
                    paymentMethod.valueToCopy?.let { valueToCopy ->
                        PaymentMethodValueUI(
                            value = value,
                            valueToCopy = valueToCopy,
                        )
                    }
                },
            )
        }
    }

    fun map(paymentMethod: PaymentMethod): PaymentMethodUI {
        return PaymentMethodUI(
            uuid = paymentMethod.uuid,
            name = when (paymentMethod.name) {
                PaymentMethodName.CASH -> R.string.msg_payment_cash
                PaymentMethodName.CARD -> R.string.msg_payment_card
                PaymentMethodName.CARD_NUMBER -> R.string.msg_payment_card_number
                PaymentMethodName.PHONE_NUMBER -> R.string.msg_payment_phone_number
            }.let { nameResId ->
                resources.getString(nameResId)
            },
            value = paymentMethod.valueToShow?.let { value ->
                paymentMethod.valueToCopy?.let { valueToCopy ->
                    PaymentMethodValueUI(
                        value = value,
                        valueToCopy = valueToCopy,
                    )
                }
            },
        )
    }

    fun map(paymentMethod: String?): String? {
        return when (paymentMethod) {
            PaymentMethodName.CASH.name -> R.string.msg_payment_cash
            PaymentMethodName.CARD.name -> R.string.msg_payment_card
            PaymentMethodName.CARD_NUMBER.name -> R.string.msg_payment_card_number
            PaymentMethodName.PHONE_NUMBER.name -> R.string.msg_payment_phone_number
            else -> null
        }?.let { nameResId ->
            resources.getString(nameResId)
        }
    }
}
