package com.bunbeauty.presentation.util.string

import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.Cafe
import com.bunbeauty.domain.model.address.CafeAddress
import com.bunbeauty.domain.model.address.UserAddress

interface IStringUtil {
    fun toStringIsDelivery(isDelivery: Boolean): String
    fun toStringTime(time: Long): String
    fun toStringOrderStatus(orderStatus: OrderStatus): String

    fun getCafeAddressString(cafeAddress: CafeAddress?): String
    fun getUserAddressString(userAddress: UserAddress?): String
    fun getWorkingHoursString(cafe: Cafe): String
    fun getAddedToCartString(productName: String): String
    fun getRemovedFromCartString(productName: String): String
    fun getDeliveryString(deliveryCost: Int): String
    fun getCostString(cost: Int?): String
    fun getTimeString(hour: Int, minute: Int): String
    fun getCodeString(code: String): String
    fun getIsClosedMessage(cafe: Cafe): String
    fun getSizeString(weight: Int?): String
    fun getCountString(count: Int): String
    fun getOrderStatusString(orderStatus: OrderStatus): String
}