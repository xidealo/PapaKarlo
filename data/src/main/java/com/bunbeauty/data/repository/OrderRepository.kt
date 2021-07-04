package com.bunbeauty.data.repository

import com.bunbeauty.data.dao.OrderDao
import com.bunbeauty.data.mapper.firebase.OrderMapper
import com.bunbeauty.domain.repo.CartProductRepo
import com.bunbeauty.domain.model.local.order.Order
import com.bunbeauty.domain.repo.ApiRepo
import com.bunbeauty.domain.model.local.order.UserOrder
import com.bunbeauty.domain.repo.OrderRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class OrderRepository @Inject constructor(
    private val apiRepo: ApiRepo,
    private val orderDao: OrderDao,
    private val cartProductRepo: CartProductRepo,
    private val orderMapper: OrderMapper,
) : OrderRepo, CoroutineScope {

    override val coroutineContext: CoroutineContext = Job()

    override suspend fun insert(order: Order): String {
        order.orderEntity.uuid =
            apiRepo.insert(orderMapper.from(order), order.cafeId)
        orderDao.insert(order.orderEntity)
        for (cardProduct in order.cartProducts) {
            cardProduct.orderId = order.orderEntity.uuid
            cartProductRepo.update(cardProduct)
        }
        subscribeOnActiveOrder(UserOrder(cafeId = order.cafeId, orderId = order.orderEntity.uuid))
        return order.orderEntity.uuid
    }

    suspend fun insertToLocal(order: Order) {
        if (orderDao.getOrderByUuid(order.orderEntity.uuid) == null) {
            for (cardProduct in order.cartProducts) {
                cardProduct.orderId = order.orderEntity.uuid
                cardProduct.uuid = UUID.randomUUID().toString()
                cartProductRepo.insertToLocal(cardProduct)
            }
        }
        orderDao.insert(order.orderEntity)
    }

    override suspend fun loadOrders(userOrderList: List<UserOrder>) {
        userOrderList.forEach { userOrder ->
            apiRepo.getOrder(userOrder.cafeId, userOrder.orderId).onEach { order ->
                if (order != null) {
                    insertToLocal(
                        order = orderMapper.to(order)
                            .also { it.orderEntity.uuid = userOrder.orderId })
                    subscribeOnActiveOrder(userOrder)
                }
            }.launchIn(this)
        }
    }

    override fun getOrdersWithCartProducts(): Flow<List<Order>> = orderDao.getOrders()
    override fun getOrdersWithCartProductsWithEmptyUserId(): Flow<List<Order>> {
        return orderDao.getOrdersByUserId("")
    }
    override fun getOrdersWithCartProductsByUserId(userId: String): Flow<List<Order>> {
        return orderDao.getOrdersByUserId(userId)
    }
    override fun getOrderWithCartProducts(orderUuid: String): Flow<Order?> {
        return orderDao.getOrderFlowByUuid(orderUuid)
    }

    private fun subscribeOnActiveOrder(userOrder: UserOrder) {
        apiRepo.getOrderWithSubscribe(userOrder.cafeId, userOrder.orderId).onEach { order ->
            if (order != null)
                insertToLocal(
                    order = orderMapper.to(order)
                        .also { it.orderEntity.uuid = userOrder.orderId })
        }.launchIn(this)
    }

}