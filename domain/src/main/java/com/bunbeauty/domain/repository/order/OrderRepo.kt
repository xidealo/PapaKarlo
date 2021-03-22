package com.bunbeauty.domain.repository.order

import androidx.lifecycle.LiveData
import com.bunbeauty.data.model.order.Order

interface OrderRepo {
    suspend fun saveOrder(order: Order)
    fun getOrdersWithCartProducts(): LiveData<List<Order>>

    //suspend fun deleteAll(orderList: List<OrderWithCartProducts>)
}