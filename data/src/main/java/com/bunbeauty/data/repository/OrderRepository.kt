package com.bunbeauty.data.repository

import com.bunbeauty.data.database.dao.OrderDao
import com.bunbeauty.data.mapper.order.IOrderMapper
import com.bunbeauty.data.network.api.ApiRepo
import com.bunbeauty.domain.mapFlow
import com.bunbeauty.domain.mapListFlow
import com.bunbeauty.domain.model.order.CreatedOrder
import com.bunbeauty.domain.model.order.LightOrder
import com.bunbeauty.domain.model.order.OrderCode
import com.bunbeauty.domain.model.order.OrderDetails
import com.bunbeauty.domain.repo.OrderRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val orderDao: OrderDao,
    private val apiRepo: ApiRepo,
    private val orderMapper: IOrderMapper,
) : BaseRepository(), OrderRepo {

    override fun observeOrderListByUserUuid(userUuid: String): Flow<List<LightOrder>> {
        return orderDao.observeOrderListByUserUuid(userUuid).mapListFlow(orderMapper::toLightOrder)
    }

    override fun observeOrderByUuid(orderUuid: String): Flow<OrderDetails?> {
        return orderDao.observeOrderByUuid(orderUuid).mapFlow(orderMapper::toOrderDetails)
    }

    override fun observeOrderUpdates(token: String): Flow<OrderDetails> {
        return apiRepo.subscribeOnOrderUpdates(token).onEach { orderServer ->
            orderDao.updateOrderStatus(orderMapper.toOrderStatusUpdate(orderServer))
        }.map { orderServer ->
            orderMapper.toOrderDetails(orderServer)
        }
    }

    override suspend fun stopCheckOrderUpdates() {
        apiRepo.unsubscribeOnOrderUpdates()
    }

    override suspend fun getOrderByUuid(orderUuid: String): OrderDetails? {
        return orderDao.getOrderByUuid(orderUuid)?.let(orderMapper::toOrderDetails)
    }

    override suspend fun createOrder(token: String, createdOrder: CreatedOrder): OrderCode? {
        val orderPostServer = orderMapper.toPostServerModel(createdOrder)
        return apiRepo.postOrder(token, orderPostServer).handleResultAndReturn { oderServer ->
            orderDao.insertOrder(orderMapper.toEntityModel(oderServer))
            orderMapper.toOrderCode(oderServer)
        }
    }
}