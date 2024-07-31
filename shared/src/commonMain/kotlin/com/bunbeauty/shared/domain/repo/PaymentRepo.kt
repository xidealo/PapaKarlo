package com.bunbeauty.shared.domain.repo

import com.bunbeauty.shared.domain.model.payment_method.PaymentMethod

interface PaymentRepo {

    suspend fun getPaymentMethodList(): List<PaymentMethod>
}
