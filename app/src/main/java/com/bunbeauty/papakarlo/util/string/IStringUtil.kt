package com.bunbeauty.papakarlo.util.string

import com.bunbeauty.papakarlo.feature.create_order.model.TimeUI
import com.bunbeauty.shared.domain.model.address.UserAddress
import com.bunbeauty.shared.domain.model.date_time.DateTime
import com.bunbeauty.shared.domain.model.date_time.Time
import com.bunbeauty.shared.domain.model.order.OrderStatus

interface IStringUtil {

    fun getUserAddressString(userAddress: UserAddress?): String?
    fun getDateTimeString(dateTime: DateTime): String
    fun getTimeString(time: Time): String
    fun getTimeString(time: TimeUI.Time): String
    fun getCostString(cost: Int?): String?
    fun getCostString(cost: Int): String
    fun getCountString(count: Int): String
    fun getOrderStatusName(orderStatus: OrderStatus): String
    fun getPickupMethodString(isDelivery: Boolean): String
}
