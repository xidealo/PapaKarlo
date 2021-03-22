package com.bunbeauty.domain.repository.api

import com.bunbeauty.data.model.cafe.Cafe
import com.bunbeauty.data.model.order.Order
import kotlinx.coroutines.flow.Flow

interface IApiRepository {
    fun insertOrder(order: Order)
    fun getCafeList(): Flow<List<Cafe>>
    fun getMenuProductList(): Flow<List<com.bunbeauty.data.model.MenuProduct>>
    fun getDeliveryCost(): Flow<com.bunbeauty.data.model.Delivery>
}