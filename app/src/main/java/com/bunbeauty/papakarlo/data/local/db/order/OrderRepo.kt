package com.bunbeauty.papakarlo.data.local.db.order

import com.bunbeauty.papakarlo.data.model.Order

interface OrderRepo {
    suspend fun insertOrder(order: Order)
}