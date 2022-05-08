package com.bunbeauty.shared.data.dao.order

import database.OrderEntity
import database.OrderWithProductEntity
import kotlinx.coroutines.flow.Flow

interface IOrderDao {

    fun insertOrderWithProductList(orderWithProductList: List<OrderWithProductEntity>)

    suspend fun getLastOrderByUserUuid(userUuid: String): OrderEntity?

    fun observeOrderWithProductListByUserUuid(userUuid: String): Flow<List<OrderWithProductEntity>>
    fun observeOrderListByUserUuid(userUuid: String): Flow<List<OrderEntity>>
    fun observeOrderWithProductListByUuid(uuid: String): Flow<List<OrderWithProductEntity>>

    fun getOrderWithProductListByUuid(uuid: String): List<OrderWithProductEntity>

    fun updateOrderStatusByUuid(uuid: String, status: String)
}