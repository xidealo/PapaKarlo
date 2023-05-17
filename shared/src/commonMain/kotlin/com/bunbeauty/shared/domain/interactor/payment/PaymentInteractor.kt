package com.bunbeauty.shared.domain.interactor.payment

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.data.repository.PaymentRepository
import com.bunbeauty.shared.domain.model.Payment

class PaymentInteractor(
    private val dataStoreRepo: DataStoreRepo,
    private val paymentRepository: PaymentRepository,
) {

    suspend fun getPayment(): Payment? {
        val token = dataStoreRepo.getToken() ?: ""
        return paymentRepository.getPayment(token)
    }
}