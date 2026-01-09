package com.bunbeauty.core.domain.payment

import com.bunbeauty.core.domain.repo.PaymentRepo

class SavePaymentMethodUseCase(
    private val paymentRepo: PaymentRepo,
) {
    suspend operator fun invoke(paymentMethodUuid: String) {
        paymentRepo.saveSelectedPaymentMethodUuid(selectedPaymentMethodUuid = paymentMethodUuid)
    }
}
