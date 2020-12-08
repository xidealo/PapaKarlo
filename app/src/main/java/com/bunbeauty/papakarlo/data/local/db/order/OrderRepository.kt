package com.bunbeauty.papakarlo.data.local.db.order

import com.bunbeauty.papakarlo.data.api.firebase.IApiRepository
import com.bunbeauty.papakarlo.data.model.Order
import javax.inject.Inject

class OrderRepository @Inject constructor(private val iApiRepository: IApiRepository) : OrderRepo {

    override suspend fun insertOrder(order: Order) {
        iApiRepository.insertOrder(order)
    }
}