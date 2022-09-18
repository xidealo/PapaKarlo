package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.data.network.model.PaymentServer
import com.bunbeauty.shared.domain.model.Payment

class PaymentRepository(
    private val networkConnector: NetworkConnector,
    private val dataStoreRepo: DataStoreRepo
) : CacheRepository<Payment>() {

    override val tag: String = "PAYMENT_TAG"

    suspend fun getPayment(token: String): Payment? {
        return getCacheOrData(
            onApiRequest = {
                networkConnector.getPayment(token)
            },
            onLocalRequest = dataStoreRepo::getPayment,
            onSaveLocally = { paymentServer ->
                dataStoreRepo.savePayment(toPayment(paymentServer))
            },
            serverToDomainModel = ::toPayment
        )
    }

    private fun toPayment(paymentServer: PaymentServer): Payment {
        return Payment(
            phoneNumber = paymentServer.phoneNumber,
            cardNumber = paymentServer.cardNumber,
        )
    }
}