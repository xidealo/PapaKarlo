package com.bunbeauty.data_firebase.repository

import com.bunbeauty.data_firebase.dao.CafeDao
import com.bunbeauty.data_firebase.dao.OrderDao
import com.bunbeauty.domain.auth.IAuthUtil
import com.bunbeauty.domain.enums.OrderStatus
import com.example.domain_firebase.model.entity.order.OrderStatusEntity
import com.example.domain_firebase.model.entity.order.OrderWithProducts
import com.example.domain_firebase.model.firebase.order.UserOrderFirebase
import com.bunbeauty.domain.model.Order
import com.example.domain_firebase.repo.FirebaseRepo
import com.bunbeauty.domain.repo.CafeRepo
import com.bunbeauty.domain.repo.OrderRepo
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val firebaseRepo: FirebaseRepo,
    private val orderDao: OrderDao,
    private val cafeDao: CafeDao,
    private val orderMapper: com.example.domain_firebase.mapper.IOrderMapper,
    private val authUtil: IAuthUtil,
) : OrderRepo {

    override fun observeOrderList(): Flow<List<Order>>? {
        return authUtil.userUuid?.let { userUuid ->
            orderDao.observeOrderListByUserUuid(userUuid).mapOrders()
        }
    }

    override fun observeOrderByUuid(orderUuid: String): Flow<Order?> {
        return orderDao.observeOrderByUuid(orderUuid).map { orderWithCartProducts ->
            orderWithCartProducts?.let {
                val cafeEntity = cafeDao.getCafeByUuid(orderWithCartProducts.order.cafeUuid)
                orderMapper.toUIModel(orderWithCartProducts, cafeEntity)
            }
        }.flowOn(IO)
    }

    override fun observeLastOrder(): Flow<Order?> {
        return orderDao.observeLastOrder().mapOrder()
    }

    override suspend fun saveOrder(order: Order) {
        val orderFirebase = orderMapper.toFirebaseModel(order)
        val orderUuid = firebaseRepo.postOrder(orderFirebase, order.cafeUuid)
        order.uuid = orderUuid
        val orderEntity = orderMapper.toEntityModel(order)
        orderDao.insert(orderEntity)
    }

    // EXTENSIONS

    private fun Flow<OrderWithProducts?>.mapOrder(): Flow<Order?> {
        return this.flowOn(IO)
            .map { orderWithProducts ->
                orderWithProducts?.let {
                    val cafeEntity = cafeDao.getCafeByUuid(orderWithProducts.order.cafeUuid)
                    orderMapper.toUIModel(orderWithProducts, cafeEntity)
                }
            }.flowOn(Default)
    }

    private fun Flow<List<OrderWithProducts>>.mapOrders(): Flow<List<Order>> {
        return this.flowOn(IO)
            .map { orderList ->
                orderList.map { orderWithProducts ->
                    val cafeEntity = cafeDao.getCafeByUuid(orderWithProducts.order.cafeUuid)
                    orderMapper.toUIModel(orderWithProducts, cafeEntity)
                }
            }.flowOn(Default)
    }

}