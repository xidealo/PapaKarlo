package com.bunbeauty.shared.domain.feature.payment

import com.bunbeauty.shared.domain.model.payment_method.PaymentMethod
import com.bunbeauty.shared.domain.repo.PaymentRepo

class GetPaymentMethodListUseCase(
    private val paymentRepo: PaymentRepo,
) {

    suspend operator fun invoke(): List<PaymentMethod> {
        return paymentRepo.getPaymentMethodList().sortedBy { paymentMethod ->
            if (paymentMethod.value == null || paymentMethod.valueToCopy == null) {
                0
            } else {
                1
            }
        }
    }
}