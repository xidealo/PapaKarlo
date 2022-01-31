package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.order.CreatedOrder
import com.bunbeauty.domain.model.order.LightOrder
import com.bunbeauty.domain.model.order.OrderCode
import com.bunbeauty.domain.model.order.OrderDetails
import kotlinx.coroutines.flow.Flow

interface OrderRepo {

    fun observeOrderListByUserUuid(userUuid: String): Flow<List<LightOrder>>
    fun observeOrderByUuid(orderUuid: String): Flow<OrderDetails?>
    fun observeOrderUpdates(token: String): Flow<OrderDetails>
    suspend fun stopCheckOrderUpdates()

    suspend fun getOrderByUuid(orderUuid: String): OrderDetails?

    suspend fun createOrder(token: String, createdOrder: CreatedOrder): OrderCode?

}