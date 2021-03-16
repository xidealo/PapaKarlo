package com.bunbeauty.papakarlo.data.api.firebase

import com.bunbeauty.papakarlo.data.model.Delivery
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.data.model.cafe.Cafe
import com.bunbeauty.papakarlo.data.model.order.Order
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface IApiRepository {
    fun insertOrder(order: Order)
    fun getCafeList(): Flow<List<Cafe>>
    fun getMenuProductList(): Flow<List<MenuProduct>>
    fun getDeliveryCost(): Flow<Delivery>
}