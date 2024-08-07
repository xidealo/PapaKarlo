package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.data.network.model.DeliveryServer
import com.bunbeauty.shared.domain.model.Delivery
import com.bunbeauty.shared.domain.repo.DeliveryRepo

class DeliveryRepository(
    private val networkConnector: NetworkConnector,
    private val dataStoreRepo: DataStoreRepo
) : CacheRepository<Delivery>(), DeliveryRepo {

    override val tag: String = "DELIVERY_TAG"

    override suspend fun getDelivery(): Delivery? {
        return getCacheOrData(
            onApiRequest = networkConnector::getDelivery,
            onLocalRequest = dataStoreRepo::getDelivery,
            onSaveLocally = { deliveryServer ->
                dataStoreRepo.saveDelivery(toDelivery(deliveryServer))
            },
            serverToDomainModel = ::toDelivery
        )
    }

    private fun toDelivery(deliveryServer: DeliveryServer): Delivery {
        return Delivery(deliveryServer.cost, deliveryServer.forFree)
    }
}
