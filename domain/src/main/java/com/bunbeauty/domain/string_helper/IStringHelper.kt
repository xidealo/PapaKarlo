package com.bunbeauty.domain.string_helper

import com.bunbeauty.data.model.CartProduct
import com.bunbeauty.data.model.MenuProduct
import com.bunbeauty.data.model.address.Address
import com.bunbeauty.data.model.cafe.CafeEntity
import com.bunbeauty.data.model.order.OrderEntity

interface IStringHelper {
    fun toString(address: Address?): String
    fun toString(orderEntity: OrderEntity): String
    fun toStringOrderType(orderEntity: OrderEntity): String
    fun toStringDeferred(orderEntity: OrderEntity): String
    fun toStringComment(orderEntity: OrderEntity): String
    fun toString(cartProducts: List<CartProduct>): String
    fun toStringWeight(menuProduct: MenuProduct): String
    fun toStringTime(orderEntity: OrderEntity): String
    fun toStringTime(hours: Int?, minutes: Int?): String
    fun toStringWorkingHours(cafeEntity: CafeEntity): String
    fun toStringCost(cost: Int): String
}