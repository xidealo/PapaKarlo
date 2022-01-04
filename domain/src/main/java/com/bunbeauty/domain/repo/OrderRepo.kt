package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.order.Order
import com.bunbeauty.domain.model.order.OrderDetails
import kotlinx.coroutines.flow.Flow

interface OrderRepo {

    fun observeOrderListByUserUuid(userUuid: String): Flow<List<Order>>

    fun observeOrderByUuid(orderUuid: String): Flow<Order?>

    suspend fun createOrder(orderDetails: OrderDetails): Order?
}