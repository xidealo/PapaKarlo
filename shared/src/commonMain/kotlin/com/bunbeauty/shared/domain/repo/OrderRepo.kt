package com.bunbeauty.shared.domain.repo

import com.bunbeauty.shared.domain.model.order.CreatedOrder
import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.domain.model.order.Order
import com.bunbeauty.shared.domain.model.order.OrderCode
import kotlinx.coroutines.flow.Flow

interface OrderRepo {

    suspend fun observeOrderUpdates(token: String): Pair<String?, Flow<Order>>
    suspend fun observeOrderListUpdates(
        token: String,
        userUuid: String,
    ): Pair<String?, Flow<List<Order>>>

    suspend fun stopOrderUpdatesObservation(uuid: String)

    suspend fun getOrderListByUserUuid(token: String, userUuid: String): List<LightOrder>
    suspend fun getLastOrderByUserUuidNetworkFirst(token: String, userUuid: String): LightOrder?
    suspend fun getLastOrderByUserUuidLocalFirst(token: String, userUuid: String): LightOrder?
    suspend fun getOrderByUuid(token: String, orderUuid: String): Order?

    suspend fun createOrder(token: String, createdOrder: CreatedOrder): OrderCode?

}