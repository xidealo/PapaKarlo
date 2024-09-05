package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.mapper.orderavailable.mapOrderAvailableServerToOrderAvailability
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.data.repository.base.CacheRepository
import com.bunbeauty.shared.domain.model.order.OrderAvailability
import com.bunbeauty.shared.domain.repo.OrderAvailableRepo

class OrderAvailableRepository(
    private val networkConnector: NetworkConnector
) : CacheRepository<OrderAvailability>(), OrderAvailableRepo {

    override val tag: String = "ORDER_AVAILABLE_TAG"

    override suspend fun getOrderAvailable(): OrderAvailability? {
        return getCacheOrData(
            onApiRequest = networkConnector::getIsOrderAvailableData,
            onLocalRequest = {
                null
            },
            onSaveLocally = {
                // stub
            },
            serverToDomainModel = mapOrderAvailableServerToOrderAvailability
        )
    }

    override fun update(orderAvailability: OrderAvailability) {
        cache = orderAvailability
    }
}
