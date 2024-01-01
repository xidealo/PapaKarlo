package com.bunbeauty.shared.data.dao.order_product

import com.bunbeauty.shared.db.OrderProductEntity

interface IOrderProductDao {
    fun insert(orderProductEntity: OrderProductEntity)
}