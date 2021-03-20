package com.bunbeauty.papakarlo.data.local.db.order

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.papakarlo.data.local.db.BaseDao
import com.bunbeauty.data.model.order.OrderEntity
import com.bunbeauty.data.model.order.Order

@Dao
interface OrderDao : BaseDao<OrderEntity> {

    @Query("SELECT * FROM OrderEntity")
    fun getOrders(): LiveData<List<Order>>
}