package com.bunbeauty.shared.domain.repo

import com.bunbeauty.shared.domain.model.order.OrderAvailability

interface OrderAvailableRepo {
    suspend fun fetchOrderAvailable(): OrderAvailability?
    suspend fun getOrderAvailable(): OrderAvailability?
    fun update(orderAvailability: OrderAvailability)
}
