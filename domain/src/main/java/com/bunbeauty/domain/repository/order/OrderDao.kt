package com.bunbeauty.domain.repository.order

import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.data.model.order.OrderEntity
import com.bunbeauty.data.model.order.Order
import com.bunbeauty.domain.repository.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao : BaseDao<OrderEntity> {

    @Query("SELECT * FROM OrderEntity")
    fun getOrders(): Flow<List<Order>>
}