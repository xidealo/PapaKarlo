package com.bunbeauty.shared.data.repository

import com.bunbeauty.core.Logger
import com.bunbeauty.shared.data.dao.lightorder.LightOrderDao
import com.bunbeauty.shared.data.dao.order.IOrderDao
import com.bunbeauty.shared.data.dao.order_addition.IOrderAdditionDao
import com.bunbeauty.shared.data.dao.order_product.IOrderProductDao
import com.bunbeauty.shared.data.mapper.order.IOrderMapper
import com.bunbeauty.shared.data.mapper.order_product.mapOrderProductServerToOrderProductEntity
import com.bunbeauty.shared.data.mapper.orderaddition.mapOrderAdditionServerToOrderAdditionEntity
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.data.network.model.order.get.LightOrderServer
import com.bunbeauty.shared.data.network.model.order.get.OrderProductServer
import com.bunbeauty.shared.data.network.model.order.get.OrderServer
import com.bunbeauty.shared.data.network.model.order.get.OrderUpdateServer
import com.bunbeauty.shared.domain.model.order.CreatedOrder
import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.domain.model.order.Order
import com.bunbeauty.shared.domain.model.order.OrderCode
import com.bunbeauty.shared.domain.model.order.OrderStatus
import com.bunbeauty.shared.domain.repo.OrderRepo
import com.bunbeauty.shared.extension.getNullableResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

private const val ORDER_LIMIT = 30
private const val TAG = "OrderRepository"

class OrderRepository(
    private val orderDao: IOrderDao,
    private val networkConnector: NetworkConnector,
    private val orderMapper: IOrderMapper,
    private val orderAdditionDao: IOrderAdditionDao,
    private val orderProductDao: IOrderProductDao,
    private val lightOrderDao: LightOrderDao
) : OrderRepo, CoroutineScope {

    private var cacheLastOrder: LightOrder? = null

    override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.IO

    override suspend fun observeOrderUpdates(token: String): Pair<String?, Flow<Order>> {
        val (uuid, orderUpdatesFlow) = observeOrderUpdatesServer(token)
        return uuid to orderUpdatesFlow.map { orderUpdateServer ->
            orderMapper.toOrder(orderDao.getOrderWithProductListByUuid(orderUpdateServer.uuid))
        }.filterNotNull()
    }

    override suspend fun observeOrderListUpdates(
        token: String,
        userUuid: String
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

    override suspend fun getOrderList(token: String): List<LightOrder> {
        return networkConnector.getLightOrderList(
            token = token,
            count = ORDER_LIMIT
        ).getNullableResult(
            onError = {
                lightOrderDao.getLightOrderList(
                    count = ORDER_LIMIT
                ).map(orderMapper::toLightOrder)
            },
            onSuccess = { orderServerList ->
                saveLightOrderListLocally(orderServerList = orderServerList.results)
                orderServerList.results.map { orderServer ->
                    orderMapper.toLightOrder(orderServer)
                }
            }
        ) ?: emptyList()
    }

    override suspend fun getLastOrderByUserUuidNetworkFirst(
        token: String,
        userUuid: String
    ): LightOrder? {
        return networkConnector.getLastOrder(
            token = token
        ).getNullableResult(
            onError = {
                orderDao.getOrderListByUserUuid(
                    userUuid = userUuid,
                    count = 1
                ).firstOrNull()
                    ?.let(orderMapper::toLightOrder)
            },
            onSuccess = { lastOrderServer ->
                saveOrderLocally(lastOrderServer)

                val lightOrder = orderMapper.toLightOrder(lastOrderServer)
                cacheLastOrder = lightOrder

                lightOrder
            }
        )
    }

    override suspend fun getLastOrderByUserUuidLocalFirst(
        token: String,
        userUuid: String
    ): LightOrder? {
        return if (cacheLastOrder == null) {
            getLastOrderByUserUuidNetworkFirst(token = token, userUuid = userUuid)
        } else {
            cacheLastOrder
        }
    }

    override suspend fun getOrderByUuid(token: String, orderUuid: String): Order? {
        return networkConnector.getOrderByUuid(token = token, uuid = orderUuid).getNullableResult(
            onError = {
                orderDao.getOrderWithProductListByUuid(orderUuid).let(orderMapper::toOrder)
            },
            onSuccess = { orderServer ->
                saveOrderLocally(orderServer)
                orderMapper.toOrder(orderServer)
            }
        )
    }

    override suspend fun createOrder(token: String, createdOrder: CreatedOrder): OrderCode? {
        val orderPostServer = orderMapper.toOrderPostServer(createdOrder)
        return networkConnector.postOrder(
            token = token,
            order = orderPostServer
        ).getNullableResult { orderCodeServer ->
            cacheLastOrder = null
            OrderCode(
                orderCodeServer.code
            )
        }
    }

    private suspend fun observeOrderUpdatesServer(token: String): Pair<String?, Flow<OrderUpdateServer>> {
        val (uuid, orderUpdatesFlow) = networkConnector.startOrderUpdatesObservation(token)
        return uuid to orderUpdatesFlow.onEach { orderUpdateServer ->
            if (orderUpdateServer.uuid == cacheLastOrder?.uuid) {
                cacheLastOrder = cacheLastOrder?.copy(
                    status = OrderStatus.valueOf(orderUpdateServer.status)
                )
            }
            orderDao.updateOrderStatusByUuid(
                uuid = orderUpdateServer.uuid,
                status = orderUpdateServer.status
            )
            lightOrderDao.updateLightOrderStatusByUuid(
                uuid = orderUpdateServer.uuid,
                status = orderUpdateServer.status
            )
        }
    }

    private suspend fun insertOrderAdditions(
        orderProductServer: OrderProductServer
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
        orderDao.insertOrder(
            orderMapper.toOrderEntity(orderServer = orderServer)
        )
        orderServer.oderProductList.forEach { orderProductServer ->
            orderProductDao.insert(
                orderProductServer.mapOrderProductServerToOrderProductEntity()
            )
            insertOrderAdditions(orderProductServer)
        }
    }

    private fun saveLightOrderListLocally(orderServerList: List<LightOrderServer>) {
        launch {
            try {
                orderServerList.forEach { lightOrderServer ->
                    lightOrderDao.insertLightOrder(
                        orderMapper.toLightOrderEntity(lightOrderServer)
                    )
                }
            } catch (exception: Exception) {
                Logger.logD(TAG, exception.printStackTrace())
            }
        }
    }

    override suspend fun clearCache() {
        cacheLastOrder = null
        lightOrderDao.deleteAll()
        orderDao.deleteAll()
        orderAdditionDao.deleteAll()
        orderProductDao.deleteAll()
    }
}
