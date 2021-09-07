package com.example.data_api.repository

import com.bunbeauty.domain.model.Order
import com.bunbeauty.domain.repo.OrderRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderRepository @Inject constructor() : OrderRepo {

    override fun observeOrderList(): Flow<List<Order>>? {
        //TODO("Not yet implemented")

        return Any() as Flow<List<Order>>?
    }

    override fun observeOrderByUuid(orderUuid: String): Flow<Order?> {
        //TODO("Not yet implemented")

        return Any() as Flow<Order?>
    }

    override fun observeLastOrder(): Flow<Order?> {
        //TODO("Not yet implemented")

        return Any() as Flow<Order?>
    }

    override suspend fun saveOrder(order: Order) {
        //TODO("Not yet implemented")
    }
}