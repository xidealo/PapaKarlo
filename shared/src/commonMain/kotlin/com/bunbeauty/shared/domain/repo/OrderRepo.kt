package com.bunbeauty.shared.domain.repo

import com.bunbeauty.shared.domain.model.order.CreatedOrder
import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.domain.model.order.Order
import com.bunbeauty.shared.domain.model.order.OrderCode
import kotlinx.coroutines.flow.Flow

interface OrderRepo {

    fun observeOrderListByUserUuid(userUuid: String): Flow<List<LightOrder>>
    fun observeLastOrderByUserUuid(userUuid: String): Flow<LightOrder?>
    fun observeOrderByUuid(orderUuid: String): Flow<Order?>
    suspend fun observeOrderUpdates(token: String): Flow<Order>
    suspend fun stopOrderUpdatesObservation()

    suspend fun getLastOrderByUserUuid(token: String, userUuid: String): LightOrder?
    suspend fun getOrderByUuid(orderUuid: String): Order?

    suspend fun createOrder(token: String, createdOrder: CreatedOrder): OrderCode?

}