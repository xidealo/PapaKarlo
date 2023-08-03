package com.bunbeauty.shared.domain.feature.payment

import com.bunbeauty.shared.DataStoreRepo

class SavePaymentMethodUseCase(
    private val dataStoreRepo: DataStoreRepo,
) {
    suspend operator fun invoke(paymentMethodUuid: String) {
        dataStoreRepo.saveSelectedPaymentMethodUuid(selectedPaymentMethodUuid = paymentMethodUuid)
    }
}