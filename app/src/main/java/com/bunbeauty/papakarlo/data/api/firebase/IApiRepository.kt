package com.bunbeauty.papakarlo.data.api.firebase

import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.data.model.Order

interface IApiRepository {
    fun insertOrder(order: Order)
    fun insertCartProduct(cartProduct: CartProduct, orderUuid: String)
}