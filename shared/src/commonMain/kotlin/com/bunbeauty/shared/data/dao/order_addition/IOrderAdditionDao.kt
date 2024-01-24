package com.bunbeauty.shared.data.dao.order_addition

import com.bunbeauty.shared.db.OrderAdditionEntity

interface IOrderAdditionDao {
    suspend fun insert(orderAdditionEntity: OrderAdditionEntity)
}