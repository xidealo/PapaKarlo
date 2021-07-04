package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.local.order.Order
import com.bunbeauty.domain.model.local.order.UserOrder
import kotlinx.coroutines.flow.Flow

interface OrderRepo {
    suspend fun insert(order: Order): String
    suspend fun loadOrders(userOrderList: List<UserOrder>)
    fun getOrdersWithCartProducts(): Flow<List<Order>>
    fun getOrdersWithCartProductsByUserId(userId: String): Flow<List<Order>>
    fun getOrderWithCartProducts(orderUuid: String): Flow<Order?>

    //suspend fun deleteAll(orderList: List<OrderWithCartProducts>)
}