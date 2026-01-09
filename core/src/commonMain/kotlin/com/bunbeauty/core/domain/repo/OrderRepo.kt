package com.bunbeauty.core.domain.repo

import com.bunbeauty.core.model.order.CreatedOrder
import com.bunbeauty.core.model.order.LightOrder
import com.bunbeauty.core.model.order.Order
import com.bunbeauty.core.model.order.OrderCode
import kotlinx.coroutines.flow.Flow

interface OrderRepo {
    suspend fun observeOrderUpdates(token: String): Pair<String?, Flow<Order>>

    suspend fun observeLightOrderListUpdates(
        token: String,
        userUuid: String,
    ): Pair<String?, Flow<List<LightOrder>>>

    suspend fun stopOrderUpdatesObservation(uuid: String)

    suspend fun getOrderList(token: String): List<LightOrder>

    // last order
    suspend fun getLastOrderByUserUuidNetworkFirst(
        token: String,
        userUuid: String,
    ): LightOrder?

    suspend fun getLastOrderByUserUuidLocalFirst(): LightOrder?

    suspend fun getOrderByUuid(
        token: String,
        orderUuid: String,
    ): Order?

    suspend fun createOrder(
        token: String,
        createdOrder: CreatedOrder,
    ): OrderCode?

    suspend fun clearCache()
}
