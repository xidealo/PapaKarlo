package com.bunbeauty.papakarlo.utils.string

import com.bunbeauty.papakarlo.data.model.Address
import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.data.model.order.OrderEntity

interface IStringHelper {
    fun toString(address: Address): String
    fun toString(orderEntity: OrderEntity): String
    fun toString(cartProducts: List<CartProduct>): String
}