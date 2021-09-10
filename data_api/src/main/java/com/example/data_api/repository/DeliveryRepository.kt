package com.example.data_api.repository

import com.bunbeauty.common.ApiResult
import com.bunbeauty.domain.model.Delivery
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.DeliveryRepo
import com.example.domain_api.repo.ApiRepo
import javax.inject.Inject

class DeliveryRepository @Inject constructor(
    private val apiRepo: ApiRepo,
    private val dataStoreRepo: DataStoreRepo
) : DeliveryRepo {

    override suspend fun refreshDelivery() {
        when (val result = apiRepo.getDelivery()) {
            is ApiResult.Success -> {
                result.data?.let { delivery ->
                    dataStoreRepo.saveDelivery(
                        Delivery(delivery.cost, delivery.forFree)
                    )
                }
            }
            is ApiResult.Error -> {
                val t = result
            }
        }

    }
}