package com.example.data_api.repository

import com.bunbeauty.domain.mapFlow
import com.bunbeauty.domain.mapListFlow
import com.bunbeauty.domain.model.order.CreatedOrder
import com.bunbeauty.domain.model.order.Order
import com.bunbeauty.domain.repo.OrderRepo
import com.example.data_api.dao.OrderDao
import com.example.domain_api.mapper.IOrderMapper
import com.example.domain_api.repo.ApiRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val orderDao: OrderDao,
    private val apiRepo: ApiRepo,
    private val orderMapper: IOrderMapper,
) : BaseRepository(), OrderRepo {

    override fun observeOrderListByUserUuid(userUuid: String): Flow<List<Order>> {
        return orderDao.observeOrderListByUserUuid(userUuid).mapListFlow(orderMapper::toModel)
    }

    override fun observeOrderByUuid(orderUuid: String): Flow<Order?> {
        return orderDao.observeOrderByUuid(orderUuid).mapFlow(orderMapper::toModel)
    }

    override suspend fun createOrder(token: String, createdOrder: CreatedOrder): Order? {
        val orderPostServer = orderMapper.toPostServerModel(createdOrder)
        return apiRepo.postOrder(token, orderPostServer)
            .handleResultAndReturn { oderServer ->
                val order = orderMapper.toEntityModel(oderServer)
                orderDao.insertOrder(order)

                orderMapper.toModel(order)
            }
    }

    override fun observeOrderUpdates(token: String): Flow<Order> {
        return apiRepo.subscribeOnOrderUpdates(token).map { orderServer ->
            orderMapper.toModel(orderServer)
        }
    }

    override suspend fun updateOrderStatus(order: Order) {
        orderDao.updateOrderStatus(orderMapper.toOrderStatusUpdate(order))
    }

    override suspend fun stopCheckOrderUpdates() {
        apiRepo.unsubscribeOnOrderUpdates()
    }
}