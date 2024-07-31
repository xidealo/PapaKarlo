package com.bunbeauty.shared.domain.repo

import com.bunbeauty.shared.domain.model.Delivery

@Deprecated("Get delivery from user address")
interface DeliveryRepo {
    @Deprecated("Get delivery from user address")
    suspend fun getDelivery(): Delivery?
}
