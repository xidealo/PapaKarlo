package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.order.CreatedOrder
import com.bunbeauty.domain.model.order.LightOrder
import com.bunbeauty.domain.model.order.Order
import kotlinx.coroutines.flow.Flow

interface OrderRepo {

    fun observeOrderListByUserUuid(userUuid: String): Flow<List<LightOrder>>

    suspend fun getOrderByUuid(orderUuid: String): Order?

    fun observeOrderByUuid(orderUuid: String): Flow<Order?>

    suspend fun createOrder(token: String, createdOrder: CreatedOrder): Order?

    fun observeOrderUpdates(token: String): Flow<Order>

    suspend fun updateOrderStatus(order: Order)

    suspend fun stopCheckOrderUpdates()
}