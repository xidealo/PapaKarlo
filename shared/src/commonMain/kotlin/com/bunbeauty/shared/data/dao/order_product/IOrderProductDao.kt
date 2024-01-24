package com.bunbeauty.shared.data.dao.order_product

import com.bunbeauty.shared.db.OrderProductEntity

interface IOrderProductDao {
    suspend fun insert(orderProductEntity: OrderProductEntity)
}