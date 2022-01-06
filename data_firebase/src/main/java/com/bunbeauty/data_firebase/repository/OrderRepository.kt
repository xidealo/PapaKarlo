package com.bunbeauty.data_firebase.repository

import com.bunbeauty.data_firebase.dao.CafeDao
import com.bunbeauty.data_firebase.dao.OrderDao
import com.bunbeauty.domain.model.order.CreatedOrder
import com.bunbeauty.domain.model.order.Order
import com.bunbeauty.domain.repo.OrderRepo
import com.example.domain_firebase.mapper.IOrderMapper
import com.example.domain_firebase.model.entity.order.OrderWithProducts
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val orderDao: OrderDao,
    private val cafeDao: CafeDao,
    private val orderMapper: IOrderMapper,
) : OrderRepo {

    override fun observeOrderByUuid(orderUuid: String): Flow<Order?> {
        return orderDao.observeOrderByUuid(orderUuid).map { orderWithCartProducts ->
            orderWithCartProducts?.let {
                val cafeEntity = cafeDao.getCafeByUuid(orderWithCartProducts.order.cafeUuid)
                orderMapper.toUIModel(orderWithCartProducts, cafeEntity)
            }
        }.flowOn(IO)
    }

    override fun observeOrderListByUserUuid(userUuid: String): Flow<List<Order>> {
        return flow { }
    }

    override suspend fun createOrder(token: String, createdOrder: CreatedOrder): Order? {
        return null
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