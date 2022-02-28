package com.bunbeauty.data.sql_delight.dao.order

import database.OrderEntity
import database.OrderWithProductEntity
import kotlinx.coroutines.flow.Flow

interface IOrderDao {

    fun insertOrderWithProductList(orderWithProductList: List<OrderWithProductEntity>)

    fun observeOrderWithProductListByUserUuid(userUuid: String): Flow<List<OrderWithProductEntity>>
    fun observeOrderListByUserUuid(userUuid: String): Flow<List<OrderEntity>>
    fun observeOrderWithProductListByUuid(uuid: String): Flow<List<OrderWithProductEntity>>
    fun observeLastOrderByUserUuid(userUuid: String): Flow<OrderEntity?>

    fun getOrderWithProductListByUuid(uuid: String): List<OrderWithProductEntity>

    fun updateOrderStatusByUuid(uuid: String, status: String)
}