package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.dao.order.IOrderDao
import com.bunbeauty.shared.data.mapper.order.IOrderMapper
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.domain.mapFlow
import com.bunbeauty.shared.domain.mapListFlow
import com.bunbeauty.shared.domain.model.order.CreatedOrder
import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.domain.model.order.Order
import com.bunbeauty.shared.domain.model.order.OrderCode
import com.bunbeauty.shared.domain.repo.OrderRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class OrderRepository(
    private val orderDao: IOrderDao,
    private val networkConnector: NetworkConnector,
    private val orderMapper: IOrderMapper,
) : BaseRepository(), OrderRepo {

    override val tag: String = "ORDER_TAG"

    override fun observeOrderListByUserUuid(userUuid: String): Flow<List<LightOrder>> {
        return orderDao.observeOrderListByUserUuid(userUuid).mapListFlow(orderMapper::toLightOrder)
    }

    override fun observeOrderByUuid(orderUuid: String): Flow<Order?> {
        return orderDao.observeOrderWithProductListByUuid(orderUuid).mapFlow(orderMapper::toOrder)
    }

    override fun observeOrderUpdates(token: String): Flow<Order> {
        return networkConnector.subscribeOnOrderUpdates(token).onEach { orderServer ->
            orderDao.updateOrderStatusByUuid(
                uuid = orderServer.uuid,
                status = orderServer.status,
            )
        }.map { orderServer ->
            orderMapper.toOrder(orderServer)
        }
    }

    override suspend fun stopCheckOrderUpdates() {
        networkConnector.unsubscribeOnOrderUpdates()
    }

    override suspend fun getOrderByUuid(orderUuid: String): Order? {
        return orderDao.getOrderWithProductListByUuid(orderUuid).let(orderMapper::toOrder)
    }

    override suspend fun createOrder(token: String, createdOrder: CreatedOrder): OrderCode? {
        val orderPostServer = orderMapper.toOrderPostServer(createdOrder)
        return networkConnector.postOrder(token, orderPostServer).getNullableResult { oderServer ->
            orderDao.insertOrderWithProductList(orderMapper.toOrderWithProductEntityList(oderServer))
            println("CHECK ITTTTTT")
            println(oderServer)
            println("RETURN FROM DB ${orderDao.getLastOrderByUserUuid(oderServer.clientUserUuid)}")
            orderMapper.toOrderCode(oderServer)
        }
    }
}