package com.bunbeauty.core.domain.repo

import com.bunbeauty.core.model.order.CreatedOrder
import com.bunbeauty.core.model.order.LightOrder
import com.bunbeauty.core.model.order.Order
import com.bunbeauty.core.model.order.OrderCode
import kotlinx.coroutines.flow.Flow

interface OrderRepo {
    suspend fun observeOrderUpdates(): Pair<String?, Flow<Order>>

    suspend fun observeLightOrderListUpdates(
    ): Pair<String?, Flow<List<LightOrder>>>

    suspend fun stopOrderUpdatesObservation(uuid: String)

    suspend fun getOrderList(): List<LightOrder>

    // last order
    suspend fun getLastOrderByUserUuidNetworkFirst(): LightOrder?

    suspend fun getLastOrderByUserUuidLocalFirst(): LightOrder?

    suspend fun getOrderByUuid(
        orderUuid: String,
    ): Order?

    suspend fun createOrder(
        createdOrder: CreatedOrder,
    ): OrderCode?

    suspend fun clearCache()
}
