package com.bunbeauty.papakarlo.utils.string

import com.bunbeauty.data.model.cafe.CafeEntity
import com.bunbeauty.data.model.order.OrderEntity

interface IStringHelper {
    fun toString(address: com.bunbeauty.data.model.Address?): String
    fun toString(orderEntity: OrderEntity): String
    fun toString(cartProducts: List<com.bunbeauty.data.model.CartProduct>): String
    fun toStringWeight(menuProduct: com.bunbeauty.data.model.MenuProduct): String
    fun toStringTime(orderEntity: OrderEntity): String
    fun toStringTime(hours: Int?, minutes: Int?): String
    fun toStringWorkingHours(cafeEntity: CafeEntity): String
    fun toStringPrice(price: Int): String
}