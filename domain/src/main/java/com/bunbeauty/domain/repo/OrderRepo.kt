package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.order.CreatedOrder
import com.bunbeauty.domain.model.order.LightOrder
import com.bunbeauty.domain.model.order.Order
import com.bunbeauty.domain.model.order.OrderCode
import kotlinx.coroutines.flow.Flow

interface OrderRepo {

    fun observeOrderListByUserUuid(userUuid: String): Flow<List<LightOrder>>
    fun observeOrderByUuid(orderUuid: String): Flow<Order?>
    fun observeOrderUpdates(token: String): Flow<Order>
    suspend fun stopCheckOrderUpdates()

    suspend fun getOrderByUuid(orderUuid: String): Order?

    suspend fun createOrder(token: String, createdOrder: CreatedOrder): OrderCode?

}