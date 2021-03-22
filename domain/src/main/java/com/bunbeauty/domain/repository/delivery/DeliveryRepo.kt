package com.bunbeauty.domain.repository.delivery

interface DeliveryRepo {
    suspend fun refreshDeliveryCost()
}