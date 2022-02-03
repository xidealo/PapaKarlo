package com.bunbeauty.papakarlo.util.string

import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.address.UserAddress
import com.bunbeauty.domain.model.cafe.CafePreview
import com.bunbeauty.domain.model.datee_time.DateTime
import com.bunbeauty.domain.model.datee_time.Time

interface IStringUtil {

    fun getUserAddressString(userAddress: UserAddress?): String?
    fun getDateTimeString(dateTime: DateTime): String
    fun getTimeString(time: Time): String
    fun getWorkingHoursString(cafe: CafePreview): String
    fun getCostString(cost: Int?): String
    fun getCountString(count: Int): String
    fun getOrderStatusName(orderStatus: OrderStatus): String
    fun getPickupMethodString(isDelivery: Boolean): String
}