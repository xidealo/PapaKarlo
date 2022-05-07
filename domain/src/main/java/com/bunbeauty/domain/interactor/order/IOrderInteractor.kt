package com.bunbeauty.domain.interactor.order

import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.domain.model.order.OrderCode
import com.bunbeauty.shared.domain.model.order.OrderWithAmounts
import kotlinx.coroutines.flow.Flow

interface IOrderInteractor {

    suspend fun observeOrderList(): Flow<List<LightOrder>>
    fun observeOrderByUuid(orderUuid: String): Flow<OrderWithAmounts?>
    suspend fun createOrder(
        isDelivery: Boolean,
        userAddressUuid: String?,
        cafeUuid: String?,
        addressDescription: String,
        comment: String?,
        deferredTime: Long?,
    ): OrderCode?
}