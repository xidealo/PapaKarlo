package com.bunbeauty.shared.domain.repo

import com.bunbeauty.shared.domain.model.Delivery

interface DeliveryRepo {
    suspend fun getDelivery(): Delivery?
}