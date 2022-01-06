package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.order.CreatedOrder
import com.bunbeauty.domain.model.order.Order
import kotlinx.coroutines.flow.Flow

interface OrderRepo {

    fun observeOrderListByUserUuid(userUuid: String): Flow<List<Order>>

    fun observeOrderByUuid(orderUuid: String): Flow<Order?>

    suspend fun createOrder(token: String, createdOrder: CreatedOrder): Order?
}