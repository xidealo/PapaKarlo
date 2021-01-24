package com.bunbeauty.papakarlo.data.local.db.order

import androidx.lifecycle.LiveData
import com.bunbeauty.papakarlo.data.model.order.Order

interface OrderRepo {
    suspend fun saveOrder(order: Order)
    fun getOrdersWithCartProducts(): LiveData<List<Order>>

    //suspend fun deleteAll(orderList: List<OrderWithCartProducts>)
}