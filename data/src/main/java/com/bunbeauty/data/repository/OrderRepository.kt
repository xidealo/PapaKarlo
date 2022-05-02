package com.bunbeauty.data.repository

import com.bunbeauty.common.Logger.ORDER_TAG
import com.bunbeauty.data.dao.order.IOrderDao
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
    private val orderDao: IOrderDao,
    private val apiRepo: ApiRepo,
    private val orderMapper: IOrderMapper,
) : BaseRepository(), OrderRepo {

    override val tag: String = ORDER_TAG

    override fun observeOrderListByUserUuid(userUuid: String): Flow<List<LightOrder>> {
        return orderDao.observeOrderListByUserUuid(userUuid).mapListFlow(orderMapper::toLightOrder)
    }

    override fun observeOrderByUuid(orderUuid: String): Flow<Order?> {
        return orderDao.observeOrderWithProductListByUuid(orderUuid).mapFlow(orderMapper::toOrder)
    }

    override fun observeOrderUpdates(token: String): Flow<Order> {
        return apiRepo.subscribeOnOrderUpdates(token).onEach { orderServer ->
            orderDao.updateOrderStatusByUuid(
                uuid = orderServer.uuid,
                status = orderServer.status,
            )
        }.map { orderServer ->
            orderMapper.toOrder(orderServer)
        }
    }

    override suspend fun stopCheckOrderUpdates() {
        apiRepo.unsubscribeOnOrderUpdates()
    }

    override suspend fun getOrderByUuid(orderUuid: String): Order? {
        return orderDao.getOrderWithProductListByUuid(orderUuid).let(orderMapper::toOrder)
    }

    override suspend fun createOrder(token: String, createdOrder: CreatedOrder): OrderCode? {
        val orderPostServer = orderMapper.toOrderPostServer(createdOrder)
        return apiRepo.postOrder(token, orderPostServer).getNullableResult { oderServer ->
            orderDao.insertOrderWithProductList(orderMapper.toOrderWithProductEntityList(oderServer))
            orderMapper.toOrderCode(oderServer)
        }
    }
}