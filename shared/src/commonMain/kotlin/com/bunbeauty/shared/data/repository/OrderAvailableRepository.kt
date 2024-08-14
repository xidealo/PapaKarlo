package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.mapper.orderavailable.mapOrderAvailableServerToOrderAvailability
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.domain.model.order.OrderAvailability
import com.bunbeauty.shared.domain.repo.OrderAvailableRepo
import com.bunbeauty.shared.extension.getNullableResult

class OrderAvailableRepository(
    private val networkConnector: NetworkConnector
) : OrderAvailableRepo {

    private var cache: OrderAvailability? = null

    override suspend fun fetchOrderAvailable(): OrderAvailability? {
        return networkConnector.getIsOrderAvailableData()
            .getNullableResult { orderAvailableServer ->
                val orderAvailable =
                    orderAvailableServer.mapOrderAvailableServerToOrderAvailability()
                cache = orderAvailable
                orderAvailable
            }
    }

    override suspend fun getOrderAvailable(): OrderAvailability? {
        return cache ?: fetchOrderAvailable()
    }

    override fun update(orderAvailability: OrderAvailability) {
        cache = orderAvailability
    }
}
