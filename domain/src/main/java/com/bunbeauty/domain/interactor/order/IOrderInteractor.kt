package com.bunbeauty.domain.interactor.order

import com.bunbeauty.domain.model.order.LightOrder
import com.bunbeauty.domain.model.order.Order
import kotlinx.coroutines.flow.Flow

interface IOrderInteractor {

    suspend fun observeOrderList(): Flow<List<LightOrder>>
    suspend fun createOrder(
        isDelivery: Boolean,
        userAddressUuid: String?,
        cafeUuid: String?,
        addressDescription: String,
        comment: String?,
        deferredTime: Long?,
    ): Order?
}