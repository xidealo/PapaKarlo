package com.bunbeauty.papakarlo.data.api.firebase

import com.bunbeauty.papakarlo.data.model.cafe.CafeEntity
import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.data.model.cafe.Cafe
import com.bunbeauty.papakarlo.data.model.order.Order
import kotlinx.coroutines.flow.Flow

interface IApiRepository {
    fun insertOrder(order: Order): String
    fun insertCartProduct(cartProduct: CartProduct): String
    fun getCafeList(): Flow<List<Cafe>>
    fun getMenuProductList()
}