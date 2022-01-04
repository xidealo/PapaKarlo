package com.example.data_api.repository

import com.bunbeauty.domain.mapFlow
import com.bunbeauty.domain.mapListFlow
import com.bunbeauty.domain.model.order.Order
import com.bunbeauty.domain.model.order.OrderDetails
import com.bunbeauty.domain.repo.Api
import com.bunbeauty.domain.repo.CartProductRepo
import com.bunbeauty.domain.repo.OrderRepo
import com.example.data_api.dao.OrderDao
import com.example.domain_api.mapper.IOrderMapper
import com.example.domain_api.repo.ApiRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val orderDao: OrderDao,
    @Api private val cartProductRepo: CartProductRepo,
    private val apiRepo: ApiRepo,
    private val orderMapper: IOrderMapper,
) : BaseRepository(), OrderRepo {

    override fun observeOrderListByUserUuid(userUuid: String): Flow<List<Order>> {
        return orderDao.observeOrderListByUserUuid(userUuid).mapListFlow(orderMapper::toModel)
    }

    override fun observeOrderByUuid(orderUuid: String): Flow<Order?> {
        return orderDao.observeOrderByUuid(orderUuid).mapFlow(orderMapper::toModel)
    }

    override suspend fun createOrder(orderDetails: OrderDetails): Order? {
        val cartProductList = cartProductRepo.getCartProductList()
        val orderPostServer = orderMapper.toPostServerModel(orderDetails, cartProductList)
        return apiRepo.postOrder(orderPostServer).handleResultAndReturn { oderServer ->
            val order = orderMapper.toEntityModel(oderServer)
            orderDao.insertOrder(order)
            cartProductRepo.deleteAllCartProducts()

            orderMapper.toModel(order)
        }
    }
}