package com.bunbeauty.shared.data.dao.order

import com.bunbeauty.shared.db.OrderEntity
import com.bunbeauty.shared.db.OrderWithProductEntity
import kotlinx.coroutines.flow.Flow

interface IOrderDao {

    fun insertOrderWithProductList(orderWithProductList: List<OrderWithProductEntity>)

    suspend fun getLastOrderByUserUuid(userUuid: String): OrderEntity?

    fun observeOrderWithProductListByUserUuid(userUuid: String): Flow<List<OrderWithProductEntity>>
    fun observeOrderListByUserUuid(userUuid: String): Flow<List<OrderEntity>>
    fun observeOrderWithProductListByUuid(uuid: String): Flow<List<OrderWithProductEntity>>
    fun observeLastOrderByUserUuid(userUuid: String): Flow<OrderEntity?>

    suspend fun getOrderWithProductListByUuid(uuid: String): List<OrderWithProductEntity>

    suspend fun updateOrderStatusByUuid(uuid: String, status: String)
}