package com.bunbeauty.domain.repository.order

import com.bunbeauty.data.dao.OrderDao
import com.bunbeauty.data.mapper.OrderMapper
import com.bunbeauty.domain.repository.cart_product.CartProductRepo
import com.bunbeauty.data.model.order.Order
import com.bunbeauty.data.api.IApiRepository
import com.bunbeauty.data.model.order.UserOrder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class OrderRepository @Inject constructor(
    private val apiRepository: IApiRepository,
    private val orderDao: OrderDao,
    private val cartProductRepo: CartProductRepo,
    private val orderMapper: OrderMapper,
) : OrderRepo, CoroutineScope {

    override val coroutineContext: CoroutineContext = Job()

    override suspend fun insert(order: Order): String {
        order.orderEntity.uuid =
            apiRepository.insert(orderMapper.from(order), order.cafeId)
        val orderEntityId = orderDao.insert(order.orderEntity)
        for (cardProduct in order.cartProducts) {
            cardProduct.orderId = orderEntityId
            cartProductRepo.update(cardProduct)
        }
        return order.orderEntity.uuid
    }

    suspend fun insertToLocal(order: Order) {
        val orderEntityId = orderDao.insert(order.orderEntity)
        for (cardProduct in order.cartProducts) {
            cardProduct.orderId = orderEntityId
            cartProductRepo.insertToLocal(cardProduct)
        }
    }

    override suspend fun loadOrders(orders: List<UserOrder>) {
        orders.forEach { userOrder ->
            apiRepository.getOrder(userOrder.cafeId, userOrder.orderId).onEach { order ->
                if (order != null)
                    insertToLocal(
                        order = orderMapper.to(order)
                            .also { it.orderEntity.uuid = userOrder.orderId })
            }.launchIn(this)
        }
    }

    override fun getOrdersWithCartProducts(): Flow<List<Order>> = orderDao.getOrders()

}