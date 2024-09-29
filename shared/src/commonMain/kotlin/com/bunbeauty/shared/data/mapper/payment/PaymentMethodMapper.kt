package com.bunbeauty.shared.data.mapper.payment

import com.bunbeauty.shared.data.network.model.PaymentMethodServer
import com.bunbeauty.shared.db.PaymentMethodEntity
import com.bunbeauty.shared.domain.model.payment_method.PaymentMethod
import com.bunbeauty.shared.domain.model.payment_method.PaymentMethodName

class PaymentMethodMapper {

    fun toPaymentMethod(paymentMethodServer: PaymentMethodServer): PaymentMethod? {
        val name = PaymentMethodName.entries.find { paymentMethodName ->
            paymentMethodName.name == paymentMethodServer.name
        } ?: return null

        return PaymentMethod(
            uuid = paymentMethodServer.uuid,
            name = name,
            valueToShow = paymentMethodServer.value,
            valueToCopy = paymentMethodServer.valueToCopy
        )
    }

    fun toPaymentMethod(paymentMethodEntity: PaymentMethodEntity): PaymentMethod? {
        val name = PaymentMethodName.entries.find { paymentMethodName ->
            paymentMethodName.name == paymentMethodEntity.name
        } ?: return null

        return PaymentMethod(
            uuid = paymentMethodEntity.uuid,
            name = name,
            valueToShow = paymentMethodEntity.value_,
            valueToCopy = paymentMethodEntity.valueToCopy
        )
    }

    fun toPaymentMethodEntity(paymentMethodServer: PaymentMethodServer): PaymentMethodEntity {
        return PaymentMethodEntity(
            uuid = paymentMethodServer.uuid,
            name = paymentMethodServer.name,
            value_ = paymentMethodServer.value,
            valueToCopy = paymentMethodServer.valueToCopy
        )
    }
}
