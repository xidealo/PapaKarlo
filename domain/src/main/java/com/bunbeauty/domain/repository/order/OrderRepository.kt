package com.bunbeauty.domain.repository.order

import com.bunbeauty.domain.repository.cart_product.CartProductRepo
import com.bunbeauty.data.model.order.Order
import com.bunbeauty.domain.repository.api.IApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val apiRepository: IApiRepository,
    private val orderDao: OrderDao,
    private val cartProductRepo: CartProductRepo,
) : OrderRepo {

    override suspend fun saveOrder(order: Order) {
        withContext(Dispatchers.IO) {
            apiRepository.insertOrder(order)
            val orderEntityId = orderDao.insert(order.orderEntity)
            for (cardProduct in order.cartProducts) {
                cardProduct.orderId = orderEntityId
                cartProductRepo.update(cardProduct)
            }
        }
    }

    override fun getOrdersWithCartProducts() = orderDao.getOrders()

}