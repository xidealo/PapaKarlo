package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface OrderRepo {

    fun observeOrderList(): Flow<List<Order>>

    fun observeOrderByUuid(orderUuid: String): Flow<Order?>

    fun observeLastOrder(): Flow<Order?>

    suspend fun saveOrder(order: Order)
}