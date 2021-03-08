package com.bunbeauty.papakarlo.data.local.db.delivery

interface DeliveryRepo {
    suspend fun refreshDeliveryCost()
}