package com.bunbeauty.shared.data.dao.payment_method

import com.bunbeauty.shared.db.FoodDeliveryDatabase
import com.bunbeauty.shared.db.PaymentMethodEntity

class PaymentMethodDao(
    foodDeliveryDatabase: FoodDeliveryDatabase,
) : IPaymentMethodDao {
    private val paymentMethodEntityQueries = foodDeliveryDatabase.paymentMethodEntityQueries

    override suspend fun insertPaymentMethodList(paymentMethodList: List<PaymentMethodEntity>) {
        paymentMethodEntityQueries.transaction {
            paymentMethodList.onEach { paymentMethod ->
                paymentMethodEntityQueries.insertPaymentMethod(
                    uuid = paymentMethod.uuid,
                    name = paymentMethod.name,
                    value_ = paymentMethod.value_,
                    valueToCopy = paymentMethod.valueToCopy,
                )
            }
        }
    }

    override suspend fun getPaymentMethodList(): List<PaymentMethodEntity> =
        paymentMethodEntityQueries.getPaymentMethodList().executeAsList()
}
