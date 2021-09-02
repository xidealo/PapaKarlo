package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.ui.OrderUI
import com.bunbeauty.domain.model.firebase.order.UserOrderFirebase
import kotlinx.coroutines.flow.Flow

interface OrderRepo {

    suspend fun refreshOrders(userOrderFirebaseList: List<UserOrderFirebase>, userUuid: String)

    fun observeOrderList(): Flow<List<OrderUI>>?

    fun observeOrderByUuid(orderUuid: String): Flow<OrderUI?>

    fun observeLastOrder(): Flow<OrderUI?>

    suspend fun saveOrder(order: OrderUI)
}