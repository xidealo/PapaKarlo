package com.bunbeauty.shared.data.dao.order

import app.cash.sqldelight.async.coroutines.awaitAsList
import com.bunbeauty.shared.db.FoodDeliveryDatabase
import com.bunbeauty.shared.db.OrderEntity
import com.bunbeauty.shared.db.OrderWithProductEntity

class OrderDao(
    foodDeliveryDatabase: FoodDeliveryDatabase,
) : IOrderDao {
    private val orderEntityQueries = foodDeliveryDatabase.orderEntityQueries

    override suspend fun insertOrder(orderEntity: OrderEntity) {
        orderEntityQueries.isnsertOrder(orderEntity)
    }

    override suspend fun getOrderListByUserUuid(
        userUuid: String,
        count: Int,
    ): List<OrderEntity> = orderEntityQueries.getOrderListByUserUuid(userUuid, count.toLong()).awaitAsList()

    override suspend fun getOrderWithProductListByUserUuid(userUuid: String): List<OrderWithProductEntity> =
        orderEntityQueries.getOrderWithProductListByUserUuid(userUuid).awaitAsList()

    override suspend fun getOrderWithProductListByUuid(uuid: String): List<OrderWithProductEntity> =
        orderEntityQueries.getOrderWithProductByUuid(uuid).awaitAsList()

    override suspend fun updateOrderStatusByUuid(
        uuid: String,
        status: String,
    ) {
        orderEntityQueries.updateOrderStatusByUuid(
            uuid = uuid,
            status = status,
        )
    }

    override suspend fun deleteAll() {
        orderEntityQueries.deleteAll()
    }
}
