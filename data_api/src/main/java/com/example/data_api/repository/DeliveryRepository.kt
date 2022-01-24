package com.example.data_api.repository

import com.bunbeauty.common.Logger.DELIVERY_TAG
import com.bunbeauty.domain.model.Delivery
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.DeliveryRepo
import com.example.data_api.handleResult
import com.example.domain_api.repo.ApiRepo
import javax.inject.Inject

class DeliveryRepository @Inject constructor(
    private val apiRepo: ApiRepo,
    private val dataStoreRepo: DataStoreRepo
) : DeliveryRepo {

    override suspend fun refreshDelivery() {
        apiRepo.getDelivery().handleResult(DELIVERY_TAG) { delivery ->
            dataStoreRepo.saveDelivery(
                Delivery(delivery.cost, delivery.forFree)
            )
        }
    }
}