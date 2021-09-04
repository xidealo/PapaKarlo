package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.ui.Order
import com.bunbeauty.domain.model.firebase.order.UserOrderFirebase
import kotlinx.coroutines.flow.Flow

interface OrderRepo {

    suspend fun refreshOrders(userOrderFirebaseList: List<UserOrderFirebase>, userUuid: String)

    fun observeOrderList(): Flow<List<Order>>?

    fun observeOrderByUuid(orderUuid: String): Flow<Order?>

    fun observeLastOrder(): Flow<Order?>

    suspend fun saveOrder(order: Order)
}