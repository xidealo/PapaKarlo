package com.bunbeauty.data.repository

import com.bunbeauty.domain.model.ui.Delivery
import com.bunbeauty.domain.repo.ApiRepo
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.DeliveryRepo
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DeliveryRepository @Inject constructor(
    private val apiRepo: ApiRepo,
    private val dataStoreRepo: DataStoreRepo
) : DeliveryRepo {

    override suspend fun refreshDelivery() {
        apiRepo.getDelivery().let { delivery ->
                dataStoreRepo.saveDelivery(
                    Delivery(delivery.cost, delivery.forFree)
                )
            }
    }
}