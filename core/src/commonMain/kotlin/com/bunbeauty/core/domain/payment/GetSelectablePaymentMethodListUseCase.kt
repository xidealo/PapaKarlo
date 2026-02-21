package com.bunbeauty.core.domain.payment

import com.bunbeauty.core.model.payment_method.SelectablePaymentMethod
import com.bunbeauty.core.domain.repo.PaymentRepo

class GetSelectablePaymentMethodListUseCase(
    private val paymentRepo: PaymentRepo,
) {
    suspend operator fun invoke(): List<SelectablePaymentMethod> {
        val paymentMethodList =
            paymentRepo.getPaymentMethodList().sortedBy { paymentMethod ->
                if (paymentMethod.valueToShow == null || paymentMethod.valueToCopy == null) {
                    0
                } else {
                    1
                }
            }

        return paymentMethodList.mapIndexed { index, paymentMethod ->
            SelectablePaymentMethod(
                paymentMethod = paymentMethod,
                isSelected = paymentMethod.uuid == paymentRepo.getSelectedPaymentMethodUuid(),
            )
        }
    }
}
