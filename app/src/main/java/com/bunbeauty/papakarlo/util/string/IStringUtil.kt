package com.bunbeauty.papakarlo.util.string

import com.bunbeauty.domain.model.order.OrderStatus
import com.bunbeauty.domain.model.address.UserAddress
import com.bunbeauty.domain.model.date_time.DateTime
import com.bunbeauty.domain.model.date_time.Time

interface IStringUtil {

    fun getUserAddressString(userAddress: UserAddress?): String?
    fun getDateTimeString(dateTime: DateTime): String
    fun getTimeString(time: Time): String
    fun getCostString(cost: Int?): String?
    fun getCostString(cost: Int): String
    fun getCountString(count: Int): String
    fun getOrderStatusName(orderStatus: OrderStatus): String
    fun getPickupMethodString(isDelivery: Boolean): String
}