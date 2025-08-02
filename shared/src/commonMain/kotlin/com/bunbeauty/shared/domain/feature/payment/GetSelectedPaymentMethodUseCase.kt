package com.bunbeauty.shared.domain.feature.payment

import com.bunbeauty.shared.domain.model.payment_method.PaymentMethod
import com.bunbeauty.shared.domain.model.payment_method.SelectablePaymentMethod

//todo tests
class GetSelectedPaymentMethodUseCase() {
    operator fun invoke(selectablePaymentMethodList: List<SelectablePaymentMethod>): PaymentMethod? {
        return selectablePaymentMethodList.find { paymentMethod ->
            paymentMethod.isSelected
        }?.paymentMethod
    }
}
