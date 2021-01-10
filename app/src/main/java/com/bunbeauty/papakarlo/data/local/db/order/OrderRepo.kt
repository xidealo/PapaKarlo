package com.bunbeauty.papakarlo.data.local.db.order

import androidx.lifecycle.LiveData
import com.bunbeauty.papakarlo.data.model.order.Order
import com.bunbeauty.papakarlo.data.model.order.OrderWithCartProducts
import com.bunbeauty.papakarlo.view_model.OrdersViewModel
import kotlinx.coroutines.Deferred

interface OrderRepo {
    suspend fun insertOrderAsync(order: Order): Deferred<Order>
    fun getOrdersWithCartProducts(): LiveData<List<OrderWithCartProducts>>

    //suspend fun deleteAll(orderList: List<OrderWithCartProducts>)
}