package com.bunbeauty.shared.domain.interactor.order

import com.bunbeauty.shared.domain.CommonFlow
import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.domain.model.order.OrderCode
import com.bunbeauty.shared.domain.model.order.OrderWithAmounts
import kotlinx.coroutines.flow.Flow

interface IOrderInteractor {

    suspend fun observeOrderList(): Flow<List<LightOrder>>
    fun observeOrderListSwift(): CommonFlow<List<LightOrder>>
    fun observeOrderByUuid(orderUuid: String): CommonFlow<OrderWithAmounts?>
    suspend fun createOrder(
        isDelivery: Boolean,
        userAddressUuid: String?,
        cafeUuid: String?,
        addressDescription: String,
        comment: String?,
        deferredTime: Long?,
    ): OrderCode?
}