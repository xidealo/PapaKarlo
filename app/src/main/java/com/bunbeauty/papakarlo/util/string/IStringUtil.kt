package com.bunbeauty.papakarlo.util.string

import com.bunbeauty.papakarlo.feature.createorder.screen.createorder.model.TimeUI
import com.bunbeauty.shared.domain.model.address.SelectableUserAddress
import com.bunbeauty.shared.domain.model.address.UserAddress
import com.bunbeauty.shared.domain.model.date_time.DateTime
import com.bunbeauty.shared.domain.model.date_time.Time
import com.bunbeauty.shared.domain.model.order.OrderAddress
import com.bunbeauty.shared.domain.model.order.OrderStatus
import com.bunbeauty.shared.presentation.cafe_list.CafeItem

interface IStringUtil {

    fun getUserAddressString(userAddress: UserAddress?): String?
    fun getUserAddressString(userAddress: SelectableUserAddress?): String?
    fun getOrderAddressString(orderAddress: OrderAddress): String
    fun getDateTimeString(dateTime: DateTime): String
    fun getTimeString(time: Time?): String
    fun getTimeString(time: TimeUI): String
    fun getCostString(cost: Int?): String?
    fun getCostString(cost: Int): String
    fun getCostString(cost: String): String
    fun getCountString(count: Int): String
    fun getCountString(count: String): String
    fun getOrderStatusName(orderStatus: OrderStatus): String
    fun getPickupMethodString(isDelivery: Boolean): String
    fun getDeferredString(isDelivery: Boolean): String

    fun getCafeStatusText(cafeOpenState: CafeItem.CafeOpenState): String
}
