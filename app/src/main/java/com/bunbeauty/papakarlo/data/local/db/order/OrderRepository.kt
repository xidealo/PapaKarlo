package com.bunbeauty.papakarlo.data.local.db.order

import androidx.lifecycle.LiveData
import com.bunbeauty.papakarlo.data.api.firebase.IApiRepository
import com.bunbeauty.papakarlo.data.model.order.Order
import com.bunbeauty.papakarlo.data.model.order.OrderWithCartProducts
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val iApiRepository: IApiRepository,
    private val orderDao: OrderDao
) : OrderRepo {

    override suspend fun insertOrderAsync(order: Order) = withContext(Dispatchers.IO) {
        async {
            order.uuid = iApiRepository.insertOrder(order)
            order.id = orderDao.insert(order)
            order
        }
    }

    override fun getOrdersWithCartProducts(): LiveData<List<OrderWithCartProducts>> {
        return orderDao.getOrders()
    }
}