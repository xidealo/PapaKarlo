package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.dao.order.IOrderDao
import com.bunbeauty.shared.data.dao.order_addition.IOrderAdditionDao
import com.bunbeauty.shared.data.dao.order_product.IOrderProductDao
import com.bunbeauty.shared.data.mapper.order.IOrderMapper
import com.bunbeauty.shared.data.mapper.orderaddition.mapOrderAdditionServerToOrderAdditionEntity
import com.bunbeauty.shared.data.mapper.order_product.mapOrderProductServerToOrderProductEntity
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.data.network.model.order.get.OrderProductServer
import com.bunbeauty.shared.data.network.model.order.get.OrderServer
import com.bunbeauty.shared.data.network.model.order.get.OrderUpdateServer
import com.bunbeauty.shared.domain.model.order.CreatedOrder
import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.domain.model.order.Order
import com.bunbeauty.shared.domain.model.order.OrderCode
import com.bunbeauty.shared.domain.repo.OrderRepo
import com.bunbeauty.shared.extension.getNullableResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class OrderRepository(
    private val orderDao: IOrderDao,
    private val networkConnector: NetworkConnector,
    private val orderMapper: IOrderMapper,
    private val orderAdditionDao: IOrderAdditionDao,
    private val orderProductDao: IOrderProductDao,
) : OrderRepo {

    data class CacheLastLightOrder(
        val lastOrder: LightOrder? = null,
        val isValid: Boolean = false,
    )

    private var cacheLastLightOrder = CacheLastLightOrder()

    override suspend fun observeOrderUpdates(token: String): Pair<String?, Flow<Order>> {
        val (uuid, orderUpdatesFlow) = observeOrderUpdatesServer(token)
        return uuid to orderUpdatesFlow.map { orderUpdateServer ->
            orderMapper.toOrder(orderDao.getOrderWithProductListByUuid(orderUpdateServer.uuid))
        }.filterNotNull()
    }

    override suspend fun observeOrderListUpdates(
        token: String,
        userUuid: String,
    ): Pair<String?, Flow<List<Order>>> {
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

    override suspend fun getLastOrderByUserUuidNetworkFirst(
        token: String,
        userUuid: String,
    ): LightOrder? {
        return networkConnector.getOrderList(token = token, count = 1).getNullableResult(
            onError = {
                orderDao.getLastOrderByUserUuid(userUuid)?.let(orderMapper::toLightOrder)
            },
            onSuccess = { orderServerList ->
                val lastOrderServer = orderServerList.results.firstOrNull()
                val lightOrder = lastOrderServer?.let { orderServer ->
                    saveOrderLocally(orderServer)
                    orderMapper.toLightOrder(orderServer)
                }

                cacheLastLightOrder = CacheLastLightOrder(
                    lastOrder = lightOrder,
                    isValid = true
                )

                lightOrder
            }
        )
    }

    override suspend fun getLastOrderByUserUuidLocalFirst(
        token: String,
        userUuid: String,
    ): LightOrder? {
        return if (cacheLastLightOrder.isValid) {
            cacheLastLightOrder.lastOrder
        } else {
            getLastOrderByUserUuidNetworkFirst(token, userUuid)
        }
    }

    override suspend fun getOrderByUuid(token: String, orderUuid: String): Order? {
        return networkConnector.getOrderList(token = token, uuid = orderUuid).getNullableResult(
            onError = {
                orderDao.getOrderWithProductListByUuid(orderUuid).let(orderMapper::toOrder)
            },
            onSuccess = { orderServerList ->
                orderServerList.results.firstOrNull()?.let { orderServer ->
                    saveOrderLocally(orderServer)
                    orderMapper.toOrder(orderServer)
                }
            }
        )
    }

    override suspend fun createOrder(token: String, createdOrder: CreatedOrder): OrderCode? {
        val orderPostServer = orderMapper.toOrderPostServer(createdOrder)
        return networkConnector.postOrder(
            token = token,
            order = orderPostServer
        ).getNullableResult { orderServer ->
            saveOrderLocally(orderServer)
            cacheLastLightOrder =
                CacheLastLightOrder(
                    lastOrder = orderMapper.toLightOrder(orderServer),
                    isValid = true
                )
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

    private suspend fun saveOrderListLocally(orderServerList: List<OrderServer>) {
        orderServerList.forEach { orderServer ->
            orderDao.insertOrder(
                orderMapper.toOrderEntity(orderServer)
            )
            orderServer.oderProductList.forEach { orderProductServer ->
                orderProductDao.insert(
                    orderProductServer.mapOrderProductServerToOrderProductEntity()
                )
                insertOrderAdditions(orderProductServer)
            }
        }
    }

    private suspend fun insertOrderAdditions(
        orderProductServer: OrderProductServer,
    ) {
        orderProductServer.additions.map { orderAdditionServer ->
            orderAdditionDao.insert(
                orderAdditionServer.mapOrderAdditionServerToOrderAdditionEntity(
                    orderProductServer.uuid
                )
            )
        }
    }

    private suspend fun saveOrderLocally(orderServer: OrderServer) {
        saveOrderListLocally(listOf(orderServer))
    }

    override suspend fun clearCache() {
        cacheLastLightOrder = CacheLastLightOrder()
    }
}