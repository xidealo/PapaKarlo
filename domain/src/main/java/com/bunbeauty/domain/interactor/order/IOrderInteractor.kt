package com.bunbeauty.domain.interactor.order

import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.order.LightOrder
import com.bunbeauty.domain.model.order.Order
import com.bunbeauty.domain.model.order.OrderCode
import com.bunbeauty.domain.model.order.OrderWithAmounts
import kotlinx.coroutines.flow.Flow

interface IOrderInteractor {

    suspend fun observeOrderList(): Flow<List<LightOrder>>
    suspend fun getOrderByUuid(orderUuid: String): OrderWithAmounts?
    fun observeOrderByUuid(orderUuid: String): Flow<Order?>
    fun observeOrderStatusByUuid(orderUuid: String): Flow<OrderStatus?>
    suspend fun createOrder(
        isDelivery: Boolean,
        userAddressUuid: String?,
        cafeUuid: String?,
        addressDescription: String,
        comment: String?,
        deferredTime: Long?,
    ): OrderCode?
}