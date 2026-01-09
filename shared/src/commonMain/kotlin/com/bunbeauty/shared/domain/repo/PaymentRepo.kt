package com.bunbeauty.shared.domain.repo

import com.bunbeauty.core.model.payment_method.PaymentMethod

interface PaymentRepo {
    suspend fun getPaymentMethodList(): List<PaymentMethod>
}
