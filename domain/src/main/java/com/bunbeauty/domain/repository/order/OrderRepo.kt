package com.bunbeauty.domain.repository.order

import com.bunbeauty.data.model.order.Order
import com.bunbeauty.data.model.order.UserOrder
import kotlinx.coroutines.flow.Flow

interface OrderRepo {
    suspend fun insert(order: Order): String
    suspend fun loadOrders(orders: List<UserOrder>)
    fun getOrdersWithCartProducts(): Flow<List<Order>>

    //suspend fun deleteAll(orderList: List<OrderWithCartProducts>)
}