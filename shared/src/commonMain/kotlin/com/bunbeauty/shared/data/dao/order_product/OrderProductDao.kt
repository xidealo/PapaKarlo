package com.bunbeauty.shared.data.dao.order_product

import com.bunbeauty.shared.db.FoodDeliveryDatabase
import com.bunbeauty.shared.db.OrderProductEntity

class OrderProductDao(foodDeliveryDatabase: FoodDeliveryDatabase) : IOrderProductDao {

    private val orderProductEntityQueries = foodDeliveryDatabase.orderProductEntityQueries

    override suspend fun insert(orderProductEntity: OrderProductEntity) {
        orderProductEntityQueries.insert(orderProductEntity)
    }
}
