package com.example.data_api.repository

import com.bunbeauty.domain.auth.AuthUtil
import com.bunbeauty.domain.model.Order
import com.bunbeauty.domain.repo.OrderRepo
import com.example.data_api.dao.OrderDao
import com.example.data_api.mapFlow
import com.example.data_api.mapListFlow
import com.example.domain_api.mapper.IOrderMapper
import com.example.domain_api.repo.ApiRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val authUtil: AuthUtil,
    private val orderDao: OrderDao,
    private val orderMapper: IOrderMapper,
    private val apiRepo: ApiRepo
) : OrderRepo {

    override fun observeOrderList(): Flow<List<Order>> {
        val userUuid = authUtil.userUuid
        return orderDao.observeOrderListByUserUuid(userUuid ?: "").mapListFlow(orderMapper::toModel)
    }

    override fun observeOrderByUuid(orderUuid: String): Flow<Order?> {
        return orderDao.observeOrderByUuid(orderUuid).mapFlow(orderMapper::toModel)
    }

    override fun observeLastOrder(): Flow<Order?> {
        val userUuid = authUtil.userUuid
        return orderDao.observeLastOrderByUserUuid(userUuid ?: "").mapFlow(orderMapper::toModel)
    }

    override suspend fun saveOrder(order: Order) {
        apiRepo.postOrder(orderMapper.toPostServerModel(order))
    }
}