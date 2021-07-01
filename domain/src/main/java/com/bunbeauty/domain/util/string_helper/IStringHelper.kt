package com.bunbeauty.domain.util.string_helper

import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.local.CartProduct
import com.bunbeauty.domain.model.local.MenuProduct
import com.bunbeauty.domain.model.local.address.Address
import com.bunbeauty.domain.model.local.cafe.CafeEntity
import com.bunbeauty.domain.model.local.order.OrderEntity

interface IStringHelper {
    fun toString(address: Address?): String
    fun toString(orderEntity: OrderEntity): String
    fun toStringIsDelivery(orderEntity: OrderEntity): String
    fun toStringDeferred(orderEntity: OrderEntity): String
    fun toStringComment(orderEntity: OrderEntity): String
    fun toString(cartProducts: List<CartProduct>): String
    fun toStringWeight(menuProduct: MenuProduct): String
    fun toStringTime(orderEntity: OrderEntity): String
    fun toStringTime(hours: Int?, minutes: Int?): String
    fun toStringWorkingHours(cafeEntity: CafeEntity): String
    fun toStringOrderStatus(orderStatus: OrderStatus): String

    //NEW
    fun getDeliveryString(deliveryCost: Int): String
    fun getCostString(cost: Int?): String
}