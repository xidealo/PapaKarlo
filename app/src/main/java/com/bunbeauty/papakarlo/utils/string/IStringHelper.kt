package com.bunbeauty.papakarlo.utils.string

import com.bunbeauty.papakarlo.data.model.Address
import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.data.model.cafe.CafeEntity
import com.bunbeauty.papakarlo.data.model.order.Order
import com.bunbeauty.papakarlo.data.model.order.OrderEntity

interface IStringHelper {
    fun toString(address: Address?): String
    fun toString(orderEntity: OrderEntity): String
    fun toString(cartProducts: List<CartProduct>): String
    fun toStringCost(menuProduct: MenuProduct): String
    fun toStringWeight(menuProduct: MenuProduct): String
    fun toStringFullPrice(cartProduct: CartProduct): String
    fun toStringFullPrice(order: Order): String
    fun toStringTime(orderEntity: OrderEntity): String
    fun toStringTime(hours: Int?, minutes: Int?): String
    fun toStringWorkingHours(cafeEntity: CafeEntity): String
}