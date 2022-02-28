package com.bunbeauty.data.sql_delight.dao.order

import database.OrderEntity
import database.OrderWithProductEntity
import kotlinx.coroutines.flow.Flow

interface IOrderDao {

    fun insertOrderWithProductList(orderWithProductList: List<OrderWithProductEntity>)

    fun observeOrderWithProductListByUserUuid(userUuid: String): Flow<List<OrderWithProductEntity>>

    fun observeLastOrderByUserUuid(userUuid: String): Flow<OrderEntity?>
}