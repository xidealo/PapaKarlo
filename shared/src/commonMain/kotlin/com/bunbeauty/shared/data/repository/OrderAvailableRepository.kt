package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.mapper.orderavailable.mapOrderAvailableServerToOrderAvailability
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.domain.model.order.OrderAvailability
import com.bunbeauty.shared.extension.getNullableResult

class OrderAvailableRepository(
    private val networkConnector: NetworkConnector,
) {

    private var cache: OrderAvailability? = null

    suspend fun fetchOrderAvailable(): OrderAvailability? {
        return networkConnector.getIsOrderAvailableData()
            .getNullableResult { orderAvailableServer ->
                val orderAvailable =
                    orderAvailableServer.mapOrderAvailableServerToOrderAvailability()
                cache = orderAvailable
                orderAvailable
            }
    }

    suspend fun getOrderAvailable(): OrderAvailability? {
        return cache ?: fetchOrderAvailable()
    }

    fun update(orderAvailability: OrderAvailability) {
        cache = orderAvailability
    }
}
