package com.bunbeauty.papakarlo.util.string

import com.bunbeauty.shared.domain.model.address.UserAddress
import com.bunbeauty.shared.domain.model.date_time.DateTime
import com.bunbeauty.shared.domain.model.date_time.Time
import com.bunbeauty.shared.domain.model.order.OrderStatus
import com.bunbeauty.shared.ui.create_order.model.TimeUI
import com.bunbeauty.shared.ui.create_order.model.UserAddressUi

interface IStringUtil {

    fun getUserAddressString(userAddress: UserAddress?): String?
    fun getUserAddressString(userAddress: UserAddressUi?): String?
    fun getDateTimeString(dateTime: DateTime): String
    fun getTimeString(time: Time): String
    fun getTimeString(time: TimeUI): String
    fun getCostString(cost: Int?): String?
    fun getCostString(cost: Int): String
    fun getCountString(count: Int): String
    fun getOrderStatusName(orderStatus: OrderStatus): String
    fun getPickupMethodString(isDelivery: Boolean): String
}
