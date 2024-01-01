package com.bunbeauty.shared.data.dao.order_addition

import com.bunbeauty.shared.db.OrderAdditionEntity

interface IOrderAdditionDao {
    fun insert(orderAdditionEntity: OrderAdditionEntity)
}