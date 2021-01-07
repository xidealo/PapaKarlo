package com.bunbeauty.papakarlo.data.api.firebase

import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.data.model.order.Order

interface IApiRepository {
    fun insertOrder(order: Order): String
    fun insertCartProduct(cartProduct: CartProduct): String
    fun getContactInfo()
    fun getMenuProductList()
}