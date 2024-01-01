package com.bunbeauty.shared.data.dao.order

import com.bunbeauty.shared.db.FoodDeliveryDatabase
import com.bunbeauty.shared.db.OrderEntity
import com.bunbeauty.shared.db.OrderWithProductEntity
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import kotlinx.coroutines.flow.Flow

class OrderDao(foodDeliveryDatabase: FoodDeliveryDatabase) : IOrderDao {

    private val orderEntityQueries = foodDeliveryDatabase.orderEntityQueries

    override fun insertOrder(orderEntity: OrderEntity) {
        orderEntityQueries.isnsertOrder(orderEntity)
    }

    override suspend fun getLastOrderByUserUuid(userUuid: String): OrderEntity? {
        return orderEntityQueries
            .getLastOrderWithProductListByUserUuid(userUuid)
            .executeAsOneOrNull()
    }

    override suspend fun getOrderListByUserUuid(userUuid: String): List<OrderEntity> {
        return orderEntityQueries.getOrderListByUserUuid(userUuid).executeAsList()
    }

    override suspend fun getOrderWithProductListByUserUuid(userUuid: String): List<OrderWithProductEntity> {
        return orderEntityQueries.getOrderWithProductListByUserUuid(userUuid).executeAsList()
    }

    override fun observeOrderWithProductListByUserUuid(userUuid: String): Flow<List<OrderWithProductEntity>> {
        return orderEntityQueries.getOrderWithProductListByUserUuid(userUuid).asFlow().mapToList()
    }

    override fun observeOrderListByUserUuid(userUuid: String): Flow<List<OrderEntity>> {
        return orderEntityQueries.getOrderListByUserUuid(userUuid).asFlow().mapToList()
    }

    override fun observeOrderWithProductListByUuid(uuid: String): Flow<List<OrderWithProductEntity>> {
        return orderEntityQueries.getOrderWithProductByUuid(uuid).asFlow().mapToList()
    }

    override fun observeLastOrderByUserUuid(userUuid: String): Flow<OrderEntity?> {
        return orderEntityQueries.getLastOrderWithProductListByUserUuid(userUuid).asFlow()
            .mapToOneOrNull()
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