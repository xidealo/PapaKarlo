package com.bunbeauty.shared.data.dao.order_addition

import com.bunbeauty.shared.db.FoodDeliveryDatabase
import com.bunbeauty.shared.db.OrderAdditionEntity

class OrderAdditionDao(foodDeliveryDatabase: FoodDeliveryDatabase) : IOrderAdditionDao {

    private val orderAdditionEntityQueries = foodDeliveryDatabase.orderAdditionEntityQueries

    override suspend fun insert(orderAdditionEntity: OrderAdditionEntity) {
        orderAdditionEntityQueries.insert(orderAdditionEntity = orderAdditionEntity)
    }

}