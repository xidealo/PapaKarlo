package com.bunbeauty.domain.repository.order

import com.bunbeauty.data.dao.OrderDao
import com.bunbeauty.data.mapper.OrderMapper
import com.bunbeauty.domain.repository.cart_product.CartProductRepo
import com.bunbeauty.data.model.order.Order
import com.bunbeauty.data.api.IApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val apiRepository: IApiRepository,
    private val orderDao: OrderDao,
    private val cartProductRepo: CartProductRepo,
    private val orderMapper: OrderMapper
) : OrderRepo {

    override suspend fun insert(order: Order) {
        withContext(Dispatchers.IO) {
            order.orderEntity.uuid =
                apiRepository.insert(orderMapper.from(order), order.cafeId)
            val orderEntityId = orderDao.insert(order.orderEntity)
            for (cardProduct in order.cartProducts) {
                cardProduct.orderId = orderEntityId
                cartProductRepo.update(cardProduct)
            }
        }
    }

    override fun getOrdersWithCartProducts(): Flow<List<Order>> = orderDao.getOrders()

}