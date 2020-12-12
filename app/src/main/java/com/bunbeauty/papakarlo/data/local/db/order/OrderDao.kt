package com.bunbeauty.papakarlo.data.local.db.order

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.papakarlo.data.local.db.BaseDao
import com.bunbeauty.papakarlo.data.model.order.Order
import com.bunbeauty.papakarlo.data.model.order.OrderWithCartProducts

@Dao
interface OrderDao : BaseDao<Order> {

    @Query("SELECT * FROM `Order`")
    fun getOrders(): LiveData<List<OrderWithCartProducts>>
}