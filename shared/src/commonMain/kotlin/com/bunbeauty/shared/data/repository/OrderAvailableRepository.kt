package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.mapper.orderavailable.mapOrderAvailableServerToOrderAvailable
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.domain.model.order.OrderAvailable
import com.bunbeauty.shared.extension.getNullableResult

class OrderAvailableRepository(
    private val networkConnector: NetworkConnector,
) {

    private var cache: OrderAvailable? = null

    suspend fun fetchOrderAvailable(): OrderAvailable? {
        return networkConnector.getIsOrderAvailableData()
            .getNullableResult { orderAvailableServer ->
                val orderAvailable = orderAvailableServer.mapOrderAvailableServerToOrderAvailable()
                cache = orderAvailable
                orderAvailable
            }
    }

    suspend fun getOrderAvailable(): OrderAvailable? {
        return cache ?: fetchOrderAvailable()
    }
}