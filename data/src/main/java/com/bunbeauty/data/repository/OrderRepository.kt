package com.bunbeauty.data.repository

import com.bunbeauty.data.dao.OrderDao
import com.bunbeauty.domain.auth.IAuthUtil
import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.mapper.IOrderMapper
import com.bunbeauty.domain.model.entity.order.OrderStatusEntity
import com.bunbeauty.domain.model.entity.order.OrderWithProducts
import com.bunbeauty.domain.model.firebase.order.UserOrderFirebase
import com.bunbeauty.domain.model.ui.OrderUI
import com.bunbeauty.domain.repo.ApiRepo
import com.bunbeauty.domain.repo.CafeRepo
import com.bunbeauty.domain.repo.CartProductRepo
import com.bunbeauty.domain.repo.OrderRepo
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val apiRepo: ApiRepo,
    private val orderDao: OrderDao,
    private val orderMapper: IOrderMapper,
    private val cafeRepo: CafeRepo,
    private val authUtil: IAuthUtil,
) : OrderRepo {

    override suspend fun refreshOrders(
        userOrderFirebaseList: List<UserOrderFirebase>,
        userUuid: String
    ) {
        withContext(IO) {
            apiRepo.removeOrderObservers()
            userOrderFirebaseList.forEach { userOrderFirebase ->
                apiRepo.getOrder(userOrderFirebase).onEach { orderFirebase ->
                    if (orderFirebase != null) {
                        val orderWithCartProducts =
                            orderMapper.toEntityModel(orderFirebase, userOrderFirebase, userUuid)
                        saveOrder(orderWithCartProducts)
                        observeActiveOrder(
                            orderWithCartProducts.order.orderStatus,
                            userOrderFirebase
                        )
                    }
                }.launchIn(this)
            }
        }
    }

    override fun observeOrderList(): Flow<List<OrderUI>>? {
        return authUtil.userUuid?.let { userUuid ->
            orderDao.observeOrderListByUserUuid(userUuid).mapOrders()
        }
    }

    override fun observeOrderByUuid(orderUuid: String): Flow<OrderUI?> {
        return orderDao.observeOrderByUuid(orderUuid).map { orderWithCartProducts ->
            orderWithCartProducts?.let {
                val cafeEntity = cafeRepo.getCafeEntityByUuid(orderWithCartProducts.order.cafeUuid)
                orderMapper.toUIModel(orderWithCartProducts, cafeEntity)
            }
        }.flowOn(IO)
    }

    override fun observeLastOrder(): Flow<OrderUI?> {
        return orderDao.observeLastOrder().mapOrder()
    }

    override suspend fun saveOrder(order: OrderUI) {
        val orderFirebase = orderMapper.toFirebaseModel(order)
        val orderUuid = apiRepo.postOrder(orderFirebase, order.cafeUuid)
        order.uuid = orderUuid
        val orderEntity = orderMapper.toEntityModel(order)
        orderDao.insert(orderEntity)
    }

    private suspend fun saveOrder(orderWithProducts: OrderWithProducts) {
        orderDao.insert(orderWithProducts)
    }

    private suspend fun observeActiveOrder(
        orderStatus: OrderStatus,
        userOrderFirebase: UserOrderFirebase
    ) {
        if (orderStatus != OrderStatus.CANCELED || orderStatus != OrderStatus.DELIVERED) {
            withContext(IO) {
                apiRepo.observeOrder(userOrderFirebase).onEach { updatedOrderFirebase ->
                    if (updatedOrderFirebase != null) {
                        val orderStatusEntity = OrderStatusEntity(
                            uuid = userOrderFirebase.orderUuid,
                            orderStatus = updatedOrderFirebase.orderEntity.orderStatus
                        )
                        orderDao.updateOrderStatus(orderStatusEntity)
                    }
                }.launchIn(this)
            }
        }
    }

    // EXTENSIONS

    private fun Flow<OrderWithProducts?>.mapOrder(): Flow<OrderUI?> {
        return this.flowOn(IO)
            .map { orderWithProducts ->
                orderWithProducts?.let {
                    val cafeEntity = cafeRepo.getCafeEntityByUuid(orderWithProducts.order.cafeUuid)
                    orderMapper.toUIModel(orderWithProducts, cafeEntity)
                }
            }.flowOn(Default)
    }

    private fun Flow<List<OrderWithProducts>>.mapOrders(): Flow<List<OrderUI>> {
        return this.flowOn(IO)
            .map { orderList ->
                orderList.map { orderWithProducts ->
                    val cafeEntity = cafeRepo.getCafeEntityByUuid(orderWithProducts.order.cafeUuid)
                    orderMapper.toUIModel(orderWithProducts, cafeEntity)
                }
            }.flowOn(Default)
    }

}