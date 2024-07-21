package com.bunbeauty.shared.data.dao.payment_method

import com.bunbeauty.shared.db.PaymentMethodEntity

interface IPaymentMethodDao {

    suspend fun insertPaymentMethodList(paymentMethodList: List<PaymentMethodEntity>)

    suspend fun getPaymentMethodList(): List<PaymentMethodEntity>
}
