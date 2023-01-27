package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.dao.order.IOrderDao
import com.bunbeauty.shared.data.mapper.order.IOrderMapper
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.data.network.model.order.get.OrderServer
import com.bunbeauty.shared.data.network.model.order.get.OrderUpdateServer
import com.bunbeauty.shared.domain.mapFlow
import com.bunbeauty.shared.domain.mapListFlow
import com.bunbeauty.shared.domain.model.order.CreatedOrder
import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.domain.model.order.Order
import com.bunbeauty.shared.domain.model.order.OrderCode
import com.bunbeauty.shared.domain.repo.OrderRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
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

    override fun observeLastOrderByUserUuid(userUuid: String): Flow<LightOrder?> {
        return orderDao.observeLastOrderByUserUuid(userUuid).mapFlow(orderMapper::toLightOrder)
    }

    override fun observeOrderByUuid(orderUuid: String): Flow<Order?> {
        return orderDao.observeOrderWithProductListByUuid(orderUuid).mapFlow(orderMapper::toOrder)
    }

    override suspend fun observeOrderUpdates(token: String): Pair<String?, Flow<Order>> {
        val (uuid, orderUpdatesFlow) = observeOrderUpdatesServer(token)
        return uuid to orderUpdatesFlow.map { orderUpdateServer ->
            orderMapper.toOrder(orderDao.getOrderWithProductListByUuid(orderUpdateServer.uuid))
        }.filterNotNull()
    }

    override suspend fun observeOrderListUpdates(token: String, userUuid: String): Pair<String?, Flow<List<Order>>> {
        val (uuid, orderUpdatesFlow) = observeOrderUpdatesServer(token)
        return uuid to orderUpdatesFlow.map {
            orderDao.getOrderWithProductListByUserUuid(userUuid).groupBy { orderWithProductEntity ->
                orderWithProductEntity.orderUuid
            }.map { (_, orderWithProductEntityList) ->
                orderMapper.toOrder(orderWithProductEntityList)
            }.filterNotNull()
        }
    }

    override suspend fun stopOrderUpdatesObservation(uuid: String) {
        networkConnector.stopOrderUpdatesObservation(uuid)
    }

    override suspend fun getOrderByUuid(orderUuid: String): Order? {
        return orderDao.getOrderWithProductListByUuid(orderUuid).let(orderMapper::toOrder)
    }

    override suspend fun getOrderListByUserUuid(token: String, userUuid: String): List<LightOrder> {
        return networkConnector.getOrderList(token = token).getNullableResult(
            onError = {
                orderDao.getOrderListByUserUuid(userUuid).map(orderMapper::toLightOrder)
            },
            onSuccess = { orderServerList ->
                saveOrderListLocally(orderServerList.results)
                orderServerList.results.map { orderServer ->
                    orderMapper.toLightOrder(orderServer)
                }
            }
        ) ?: emptyList()
    }

    override suspend fun getLastOrderByUserUuid(token: String, userUuid: String): LightOrder? {
        return networkConnector.getOrderList(token = token, count = 1).getNullableResult(
            onError = {
                orderDao.getLastOrderByUserUuid(userUuid)?.let(orderMapper::toLightOrder)
            },
            onSuccess = { orderServerList ->
                orderServerList.results.firstOrNull()?.let { orderServer ->
                    saveOrderLocally(orderServer)
                    orderMapper.toLightOrder(orderServer)
                }
            }
        )
    }

    override suspend fun createOrder(token: String, createdOrder: CreatedOrder): OrderCode? {
        val orderPostServer = orderMapper.toOrderPostServer(createdOrder)
        return networkConnector.postOrder(token, orderPostServer).getNullableResult { orderServer ->
            saveOrderLocally(orderServer)
            orderMapper.toOrderCode(orderServer)
        }
    }

    private suspend fun observeOrderUpdatesServer(token: String): Pair<String?, Flow<OrderUpdateServer>> {
        val (uuid, orderUpdatesFlow) = networkConnector.startOrderUpdatesObservation(token)
        return uuid to orderUpdatesFlow.onEach { orderUpdateServer ->
            orderDao.updateOrderStatusByUuid(
                uuid = orderUpdateServer.uuid,
                status = orderUpdateServer.status,
            )
        }
    }

    private fun saveOrderListLocally(orderServerList: List<OrderServer>) {
        orderServerList.flatMap { orderServer ->
            orderServer.oderProductList.map { oderProductServer ->
                orderMapper.toOrderWithProductEntity(orderServer, oderProductServer)
            }
        }.let { orderWithProductEntityList ->
            orderDao.insertOrderWithProductList(orderWithProductEntityList)
        }
    }

    private fun saveOrderLocally(orderServer: OrderServer) {
        saveOrderListLocally(listOf(orderServer))
    }
}