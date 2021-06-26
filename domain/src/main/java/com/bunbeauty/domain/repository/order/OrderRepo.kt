package com.bunbeauty.domain.repository.order

import com.bunbeauty.data.model.order.Order
import com.bunbeauty.data.model.order.UserOrder
import kotlinx.coroutines.flow.Flow

interface OrderRepo {
    suspend fun insert(order: Order): String
    suspend fun loadOrders(userOrderList: List<UserOrder>)
    fun getOrdersWithCartProducts(): Flow<List<Order>>
    fun getOrderWithCartProducts(orderUuid:String): Flow<Order?>

    //suspend fun deleteAll(orderList: List<OrderWithCartProducts>)
}