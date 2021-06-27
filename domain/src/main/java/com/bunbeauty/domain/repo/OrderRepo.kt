package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.order.Order
import com.bunbeauty.domain.model.order.UserOrder
import kotlinx.coroutines.flow.Flow

interface OrderRepo {
    suspend fun insert(order: Order): String
    suspend fun loadOrders(userOrderList: List<UserOrder>)
    fun getOrdersWithCartProducts(): Flow<List<Order>>
    fun getOrderWithCartProducts(orderUuid:String): Flow<Order?>

    //suspend fun deleteAll(orderList: List<OrderWithCartProducts>)
}