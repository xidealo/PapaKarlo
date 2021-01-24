package com.bunbeauty.papakarlo.data.local.db.order

import androidx.lifecycle.LiveData
import com.bunbeauty.papakarlo.data.api.firebase.IApiRepository
import com.bunbeauty.papakarlo.data.local.db.cart_product.CartProductRepo
import com.bunbeauty.papakarlo.data.local.db.menu_product.MenuProductRepo
import com.bunbeauty.papakarlo.data.model.order.Order
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

    override fun getOrdersWithCartProducts(): LiveData<List<Order>> {
        return orderDao.getOrders()
    }
}