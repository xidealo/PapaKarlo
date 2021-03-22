package com.bunbeauty.domain.repository.order

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.data.model.order.OrderEntity
import com.bunbeauty.data.model.order.Order
import com.bunbeauty.domain.repository.BaseDao

@Dao
interface OrderDao : BaseDao<OrderEntity> {

    @Query("SELECT * FROM OrderEntity")
    fun getOrders(): LiveData<List<Order>>
}