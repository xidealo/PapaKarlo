package com.bunbeauty.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.domain.model.local.order.OrderEntity
import com.bunbeauty.domain.model.local.order.Order
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao : BaseDao<OrderEntity> {

    @Query("SELECT * FROM OrderEntity")
    fun getOrders(): Flow<List<Order>>

    @Query("SELECT * FROM OrderEntity where userId = :userId")
    fun getOrdersByUserId(userId: String): Flow<List<Order>>

    @Query("SELECT * FROM OrderEntity where uuid = :orderUuid")
    fun getOrderFlowByUuid(orderUuid: String): Flow<Order?>

    @Query("SELECT * FROM OrderEntity WHERE uuid = :uuid")
    fun getOrderByUuid(uuid: String): Order?
}