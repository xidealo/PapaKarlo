package com.bunbeauty.core.domain.payment

import com.bunbeauty.core.model.payment_method.PaymentMethod
import com.bunbeauty.core.model.payment_method.SelectablePaymentMethod

class GetSelectedPaymentMethodUseCase {
    operator fun invoke(selectablePaymentMethodList: List<SelectablePaymentMethod>): PaymentMethod? =
        selectablePaymentMethodList
            .find { paymentMethod ->
                paymentMethod.isSelected
            }?.paymentMethod
}
