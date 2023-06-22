package com.bunbeauty.shared.domain.feature.payment

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.model.payment_method.SelectablePaymentMethod
import com.bunbeauty.shared.domain.repo.PaymentRepo
import kotlinx.coroutines.flow.first

class SavePaymentMethodUseCase(
    private val dataStoreRepo: DataStoreRepo,
) {
    suspend operator fun invoke(paymentMethodUuid: String) {
        dataStoreRepo.saveSelectedPaymentMethodUuid(selectedPaymentMethodUuid = paymentMethodUuid)
    }
}