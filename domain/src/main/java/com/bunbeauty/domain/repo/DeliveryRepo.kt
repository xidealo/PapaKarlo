package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.Delivery

interface DeliveryRepo {
    suspend fun getDelivery(): Delivery?
}