package com.bunbeauty.shared.data.dao.order

import com.bunbeauty.shared.db.OrderEntity
import com.bunbeauty.shared.db.OrderWithProductEntity

interface IOrderDao {

    suspend fun insertOrder(orderEntity: OrderEntity)

    suspend fun getOrderListByUserUuid(userUuid: String, count: Int): List<OrderEntity>
    suspend fun getOrderWithProductListByUserUuid(userUuid: String): List<OrderWithProductEntity>
    suspend fun getOrderWithProductListByUuid(uuid: String): List<OrderWithProductEntity>

    suspend fun updateOrderStatusByUuid(uuid: String, status: String)
}
