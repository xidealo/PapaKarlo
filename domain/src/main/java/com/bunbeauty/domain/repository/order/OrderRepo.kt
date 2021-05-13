package com.bunbeauty.domain.repository.order

import com.bunbeauty.data.model.order.Order
import kotlinx.coroutines.flow.Flow

interface OrderRepo {
    suspend fun insert(order: Order)
    fun getOrdersWithCartProducts(): Flow<List<Order>>

    //suspend fun deleteAll(orderList: List<OrderWithCartProducts>)
}