package com.bunbeauty.shared.data.repository

import com.bunbeauty.common.Logger.DELIVERY_TAG
import com.bunbeauty.shared.data.network.api.ApiRepo
import com.bunbeauty.shared.data.network.model.DeliveryServer
import com.bunbeauty.shared.domain.model.Delivery
import com.bunbeauty.shared.domain.repo.DataStoreRepo
import com.bunbeauty.shared.domain.repo.DeliveryRepo

class DeliveryRepository(
    private val apiRepo: ApiRepo,
    private val dataStoreRepo: DataStoreRepo
) : CacheRepository<Delivery>(), DeliveryRepo {

    override val tag: String = DELIVERY_TAG

    override suspend fun getDelivery(): Delivery? {
        return getCacheOrData(
            onApiRequest = apiRepo::getDelivery,
            onLocalRequest = dataStoreRepo::getDelivery,
            onSaveLocally = { deliveryServer ->
                dataStoreRepo.saveDelivery(toDelivery(deliveryServer))
            },
            serverToDomainModel = ::toDelivery
        )
    }

    fun toDelivery(deliveryServer: DeliveryServer): Delivery {
        return Delivery(deliveryServer.cost, deliveryServer.forFree)
    }
}