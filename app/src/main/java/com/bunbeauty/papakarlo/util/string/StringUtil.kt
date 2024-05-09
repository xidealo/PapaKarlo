package com.bunbeauty.papakarlo.util.string

import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.feature.createorder.screen.createorder.model.TimeUI
import com.bunbeauty.papakarlo.util.resources.IResourcesProvider
import com.bunbeauty.shared.Constants.ADDRESS_DIVIDER
import com.bunbeauty.shared.domain.model.address.SelectableUserAddress
import com.bunbeauty.shared.domain.model.address.UserAddress
import com.bunbeauty.shared.domain.model.date_time.DateTime
import com.bunbeauty.shared.domain.model.date_time.Time
import com.bunbeauty.shared.domain.model.order.OrderAddress
import com.bunbeauty.shared.domain.model.order.OrderStatus
import com.bunbeauty.shared.presentation.cafe_list.CafeItem

class StringUtil(
    private val resourcesProvider: IResourcesProvider
) : IStringUtil {

    override fun getCostString(cost: Int?): String? {
        return cost?.let {
            getCostString(cost)
        }
    }

    override fun getCostString(cost: Int): String {
        return cost.toString() + resourcesProvider.getString(R.string.part_ruble)
    }

    override fun getCostString(cost: String): String {
        return cost + resourcesProvider.getString(R.string.part_ruble)
    }

    override fun getUserAddressString(userAddress: UserAddress?): String? {
        return userAddress?.let {
            val houseShort = resourcesProvider.getString(R.string.msg_address_house_short)
            val flatShort = resourcesProvider.getString(R.string.msg_address_flat_short)
            val entranceShort = resourcesProvider.getString(R.string.msg_address_entrance_short)
            val floorShort = resourcesProvider.getString(R.string.msg_address_floor_short)
            userAddress.street +
                getStringPart(ADDRESS_DIVIDER, houseShort, userAddress.house) +
                getStringPart(ADDRESS_DIVIDER, flatShort, userAddress.flat) +
                getInvertedStringPart(ADDRESS_DIVIDER, userAddress.entrance, entranceShort) +
                getInvertedStringPart(ADDRESS_DIVIDER, userAddress.floor, floorShort) +
                getStringPart(ADDRESS_DIVIDER, "", userAddress.comment)
        }
    }

    override fun getUserAddressString(selectableUserAddress: SelectableUserAddress?): String? {
        return selectableUserAddress?.let {
            val houseShort = resourcesProvider.getString(R.string.msg_address_house_short)
            val flatShort = resourcesProvider.getString(R.string.msg_address_flat_short)
            val entranceShort = resourcesProvider.getString(R.string.msg_address_entrance_short)
            val floorShort = resourcesProvider.getString(R.string.msg_address_floor_short)
            selectableUserAddress.address.street +
                getStringPart(ADDRESS_DIVIDER, houseShort, selectableUserAddress.address.house) +
                getStringPart(ADDRESS_DIVIDER, flatShort, selectableUserAddress.address.flat) +
                getInvertedStringPart(ADDRESS_DIVIDER, selectableUserAddress.address.entrance, entranceShort) +
                getInvertedStringPart(ADDRESS_DIVIDER, selectableUserAddress.address.floor, floorShort) +
                getStringPart(ADDRESS_DIVIDER, "", selectableUserAddress.address.comment)
        }
    }

    override fun getOrderAddressString(orderAddress: OrderAddress): String {
        return if (orderAddress.description.isNullOrEmpty()) {
            val houseShort = resourcesProvider.getString(R.string.msg_address_house_short)
            val flatShort = resourcesProvider.getString(R.string.msg_address_flat_short)
            val entranceShort = resourcesProvider.getString(R.string.msg_address_entrance_short)
            val floorShort = resourcesProvider.getString(R.string.msg_address_floor_short)
            orderAddress.street +
                getStringPart(ADDRESS_DIVIDER, houseShort, orderAddress.house) +
                getStringPart(ADDRESS_DIVIDER, flatShort, orderAddress.flat) +
                getInvertedStringPart(ADDRESS_DIVIDER, orderAddress.entrance, entranceShort) +
                getInvertedStringPart(ADDRESS_DIVIDER, orderAddress.floor, floorShort) +
                getStringPart(ADDRESS_DIVIDER, "", orderAddress.comment)
        } else {
            orderAddress.description ?: ""
        }
    }

    override fun getDateTimeString(dateTime: DateTime): String {
        val monthName = when (dateTime.date.monthNumber) {
            1 -> R.string.month_january
            2 -> R.string.month_february
            3 -> R.string.month_march
            4 -> R.string.month_april
            5 -> R.string.month_may
            6 -> R.string.month_june
            7 -> R.string.month_july
            8 -> R.string.month_august
            9 -> R.string.month_september
            10 -> R.string.month_october
            11 -> R.string.month_november
            12 -> R.string.month_december
            else -> R.string.month_unknown
        }.let { monthResourceId ->
            resourcesProvider.getString(monthResourceId)
        }
        return "${dateTime.date.dayOfMonth} $monthName ${getTimeString(dateTime.time)}"
    }

    override fun getTimeString(time: Time?): String {
        return if (time == null) {
            resourcesProvider.getString(R.string.asap)
        } else {
            "${addFirstZero(time.hours)}:${addFirstZero(time.minutes)}"
        }
    }

    override fun getTimeString(time: TimeUI): String {
        return when (time) {
            is TimeUI.ASAP -> {
                resourcesProvider.getString(R.string.asap)
            }

            is TimeUI.Time -> {
                "${addFirstZero(time.hours)}:${addFirstZero(time.minutes)}"
            }
        }
    }

    fun getStringPart(divider: String, description: String, data: Any?): String {
        return if (data == null || data.toString().isEmpty()) {
            ""
        } else {
            divider + description + data
        }
    }

    fun getInvertedStringPart(divider: String, data: Any?, description: String): String {
        return if (data == null || data.toString().isEmpty()) {
            ""
        } else {
            divider + data + description
        }
    }

    private fun addFirstZero(number: Int): String {
        return if (number < 10) {
            "0$number"
        } else {
            number.toString()
        }
    }

    override fun getCountString(count: Int): String {
        return "× $count"
    }

    override fun getCountString(count: String): String {
        return "× $count"
    }

    override fun getOrderStatusName(orderStatus: OrderStatus): String {
        return when (orderStatus) {
            OrderStatus.NOT_ACCEPTED -> resourcesProvider.getString(R.string.msg_status_not_accepted)
            OrderStatus.ACCEPTED -> resourcesProvider.getString(R.string.msg_status_accepted)
            OrderStatus.PREPARING -> resourcesProvider.getString(R.string.msg_status_preparing)
            OrderStatus.SENT_OUT -> resourcesProvider.getString(R.string.msg_status_sent_out)
            OrderStatus.DELIVERED -> resourcesProvider.getString(R.string.msg_status_delivered)
            OrderStatus.DONE -> resourcesProvider.getString(R.string.msg_status_done)
            OrderStatus.CANCELED -> resourcesProvider.getString(R.string.msg_status_canceled)
        }
    }

    override fun getPickupMethodString(isDelivery: Boolean): String {
        return if (isDelivery) {
            resourcesProvider.getString(R.string.msg_delivery)
        } else {
            resourcesProvider.getString(R.string.msg_pickup)
        }
    }

    override fun getDeferredString(isDelivery: Boolean): String {
        return if (isDelivery) {
            resourcesProvider.getString(R.string.delivery_time)
        } else {
            resourcesProvider.getString(R.string.pickup_time)
        }
    }

    override fun getCafeStatusText(cafeOpenState: CafeItem.CafeOpenState): String {
        return when (cafeOpenState) {
            is CafeItem.CafeOpenState.Opened -> resourcesProvider.getString(R.string.msg_cafe_open)
            is CafeItem.CafeOpenState.CloseSoon -> {
                resourcesProvider.getString(R.string.msg_cafe_close_soon) +
                    cafeOpenState.time +
                    getMinuteString(cafeOpenState.time)
            }

            is CafeItem.CafeOpenState.Closed -> resourcesProvider.getString(R.string.msg_cafe_closed)
        }
    }

    private fun getMinuteString(closeIn: Int): String {
        val minuteStringId = when {
            (closeIn / 10 == 1) -> R.string.msg_cafe_minutes
            (closeIn % 10 == 1) -> R.string.msg_cafe_minute
            (closeIn % 10 in 2..4) -> R.string.msg_cafe_minutes_234
            else -> R.string.msg_cafe_minutes
        }
        return resourcesProvider.getString(minuteStringId)
    }
}
