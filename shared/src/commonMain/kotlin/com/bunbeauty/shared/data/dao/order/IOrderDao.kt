package com.bunbeauty.shared.data.dao.order

import com.bunbeauty.shared.db.OrderEntity
import com.bunbeauty.shared.db.OrderWithProductEntity
import kotlinx.coroutines.flow.Flow

interface IOrderDao {

    fun insertOrder(orderEntity: OrderEntity)

    suspend fun getLastOrderByUserUuid(userUuid: String): OrderEntity?
    suspend fun getOrderListByUserUuid(userUuid: String): List<OrderEntity>
    suspend fun getOrderWithProductListByUserUuid(userUuid: String): List<OrderWithProductEntity>
    suspend fun getOrderWithProductListByUuid(uuid: String): List<OrderWithProductEntity>

    fun observeOrderWithProductListByUserUuid(userUuid: String): Flow<List<OrderWithProductEntity>>
    fun observeOrderWithProductListByUuid(uuid: String): Flow<List<OrderWithProductEntity>>
    fun observeOrderListByUserUuid(userUuid: String): Flow<List<OrderEntity>>
    fun observeLastOrderByUserUuid(userUuid: String): Flow<OrderEntity?>

    suspend fun updateOrderStatusByUuid(uuid: String, status: String)
}