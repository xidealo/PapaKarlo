package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.entity.order.Order
import com.bunbeauty.domain.model.ui.order.OrderUI
import com.bunbeauty.domain.model.ui.order.UserOrder
import kotlinx.coroutines.flow.Flow

interface OrderRepo {
    suspend fun insert(order: Order): String
    suspend fun loadOrders(userOrderList: List<UserOrder>)
    fun getOrdersWithCartProducts(): Flow<List<Order>>
    fun getOrdersWithCartProductsWithEmptyUserId(): Flow<List<Order>>
    fun getOrdersWithCartProductsByUserId(userId: String): Flow<List<Order>>
    fun getOrderWithCartProducts(orderUuid: String): Flow<Order?>

    suspend fun saveOrder(order: OrderUI)

    //suspend fun deleteAll(orderList: List<OrderWithCartProducts>)
}