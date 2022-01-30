package com.bunbeauty.data.repository

import com.bunbeauty.common.Logger.DELIVERY_TAG
import com.bunbeauty.data.handleResult
import com.bunbeauty.data.network.api.ApiRepo
import com.bunbeauty.domain.model.Delivery
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.DeliveryRepo
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