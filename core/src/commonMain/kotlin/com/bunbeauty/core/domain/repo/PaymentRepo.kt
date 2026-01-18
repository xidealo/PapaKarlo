package com.bunbeauty.core.domain.repo

import com.bunbeauty.core.model.payment_method.PaymentMethod

interface PaymentRepo {
    suspend fun getPaymentMethodList(): List<PaymentMethod>
    suspend fun getSelectedPaymentMethodUuid(): String?
    suspend fun saveSelectedPaymentMethodUuid(selectedPaymentMethodUuid: String)
}
