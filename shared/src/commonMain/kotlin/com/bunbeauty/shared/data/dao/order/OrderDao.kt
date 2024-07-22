package com.bunbeauty.shared.data.dao.order

import com.bunbeauty.shared.db.FoodDeliveryDatabase
import com.bunbeauty.shared.db.OrderEntity
import com.bunbeauty.shared.db.OrderWithProductEntity

class OrderDao(foodDeliveryDatabase: FoodDeliveryDatabase) : IOrderDao {

    private val orderEntityQueries = foodDeliveryDatabase.orderEntityQueries

    override suspend fun insertOrder(orderEntity: OrderEntity) {
        orderEntityQueries.isnsertOrder(orderEntity)
    }

    override suspend fun getOrderListByUserUuid(
        userUuid: String,
        count: Int
    ): List<OrderEntity> {
        return orderEntityQueries.getOrderListByUserUuid(userUuid, count.toLong()).executeAsList()
    }

    override suspend fun getOrderWithProductListByUserUuid(userUuid: String): List<OrderWithProductEntity> {
        return orderEntityQueries.getOrderWithProductListByUserUuid(userUuid).executeAsList()
    }

    override suspend fun getOrderWithProductListByUuid(uuid: String): List<OrderWithProductEntity> {
        return orderEntityQueries.getOrderWithProductByUuid(uuid).executeAsList()
    }

    override suspend fun updateOrderStatusByUuid(uuid: String, status: String) {
        orderEntityQueries.updateOrderStatusByUuid(
            uuid = uuid,
            status = status
        )
    }
}
