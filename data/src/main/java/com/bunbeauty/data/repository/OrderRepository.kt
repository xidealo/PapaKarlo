package com.bunbeauty.data.repository

import com.bunbeauty.data.database.dao.OrderDao
import com.bunbeauty.data.mapper.order.IOrderMapper
import com.bunbeauty.data.network.api.ApiRepo
import com.bunbeauty.domain.mapFlow
import com.bunbeauty.domain.mapListFlow
import com.bunbeauty.domain.model.order.CreatedOrder
import com.bunbeauty.domain.model.order.LightOrder
import com.bunbeauty.domain.model.order.Order
import com.bunbeauty.domain.repo.OrderRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val orderDao: OrderDao,
    private val apiRepo: ApiRepo,
    private val orderMapper: IOrderMapper,
) : BaseRepository(), OrderRepo {

    override fun observeOrderListByUserUuid(userUuid: String): Flow<List<LightOrder>> {
        return orderDao.observeOrderListByUserUuid(userUuid).mapListFlow(orderMapper::toLightOrder)
    }

    override suspend fun getOrderByUuid(orderUuid: String): Order? {
        return orderDao.getOrderByUuid(orderUuid)?.let(orderMapper::toModel)
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