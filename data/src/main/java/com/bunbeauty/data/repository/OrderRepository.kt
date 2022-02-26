package com.bunbeauty.data.repository

import com.bunbeauty.data.database.dao.OrderDao
import com.bunbeauty.data.mapper.order.IOrderMapper
import com.bunbeauty.data.network.api.ApiRepo
import com.bunbeauty.domain.mapFlow
import com.bunbeauty.domain.mapListFlow
import com.bunbeauty.domain.model.order.CreatedOrder
import com.bunbeauty.domain.model.order.LightOrder
import com.bunbeauty.domain.model.order.Order
import com.bunbeauty.domain.model.order.OrderCode
import com.bunbeauty.domain.repo.OrderRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class OrderRepository(
    private val orderDao: OrderDao,
    private val apiRepo: ApiRepo,
    private val orderMapper: IOrderMapper,
) : BaseRepository(), OrderRepo {

    override fun observeOrderListByUserUuid(userUuid: String): Flow<List<LightOrder>> {
        return orderDao.observeOrderListByUserUuid(userUuid).mapListFlow(orderMapper::toLightOrder)
    }

    override fun observeOrderByUuid(orderUuid: String): Flow<Order?> {
        return orderDao.observeOrderByUuid(orderUuid).mapFlow(orderMapper::toOrder)
    }

    override fun observeOrderUpdates(token: String): Flow<Order> {
        return apiRepo.subscribeOnOrderUpdates(token).onEach { orderServer ->
            orderDao.updateOrderStatus(orderMapper.toOrderStatusUpdate(orderServer))
        }.map { orderServer ->
            orderMapper.toOrder(orderServer)
        }
    }

    override suspend fun stopCheckOrderUpdates() {
        apiRepo.unsubscribeOnOrderUpdates()
    }

    override suspend fun getOrderByUuid(orderUuid: String): Order? {
        return orderDao.getOrderByUuid(orderUuid)?.let(orderMapper::toOrder)
    }

    override suspend fun createOrder(token: String, createdOrder: CreatedOrder): OrderCode? {
        val orderPostServer = orderMapper.toOrderPostServer(createdOrder)
        return apiRepo.postOrder(token, orderPostServer).handleResultAndReturn { oderServer ->
            orderDao.insertOrder(orderMapper.toOrderEntityWithProducts(oderServer))
            orderMapper.toOrderCode(oderServer)
        }
    }
}