package com.bunbeauty.domain.util.string_helper

import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.ui.CartProduct
import com.bunbeauty.domain.model.ui.MenuProduct
import com.bunbeauty.domain.model.ui.address.Address
import com.bunbeauty.domain.model.ui.cafe.CafeEntity
import com.bunbeauty.domain.model.entity.order.OrderEntity

interface IStringUtil {
    fun toString(address: Address?): String
    fun toStringIsDelivery(orderEntity: OrderEntity): String
    fun toStringDeferred(orderEntity: OrderEntity): String
    fun toStringComment(orderEntity: OrderEntity): String
    fun toStringWeight(menuProduct: MenuProduct): String
    fun toStringTime(orderEntity: OrderEntity): String
    @Deprecated("")
    fun toStringTime(hours: Int?, minutes: Int?): String
    fun toStringWorkingHours(cafeEntity: CafeEntity): String
    fun toStringOrderStatus(orderStatus: OrderStatus): String
    fun getAddedToCartString(productName: String): String
    fun getRemovedFromCartString(productName: String): String
    fun getDeliveryString(deliveryCost: Int): String
    fun getCostString(cost: Int?): String
    fun getTimeString(hour: Int, minute: Int): String
    fun getCodeString(code: String): String
}