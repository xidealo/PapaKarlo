package com.bunbeauty.papakarlo.util.string

import com.bunbeauty.shared.Constants.ADDRESS_DIVIDER
import com.bunbeauty.shared.domain.model.order.OrderStatus
import com.bunbeauty.shared.domain.model.order.OrderStatus.*
import com.bunbeauty.shared.domain.model.address.UserAddress
import com.bunbeauty.shared.domain.model.date_time.DateTime
import com.bunbeauty.shared.domain.model.date_time.Time
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.util.resources.IResourcesProvider

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

    override fun getUserAddressString(userAddress: UserAddress?): String? {
        return if (userAddress == null) {
            null
        } else {
            val houseShort = resourcesProvider.getString(R.string.msg_address_house_short)
            val flatShort = resourcesProvider.getString(R.string.msg_address_flat_short)
            val entranceShort = resourcesProvider.getString(R.string.msg_address_entrance_short)
            val floorShort = resourcesProvider.getString(R.string.msg_address_floor_short)
            userAddress.street.name +
                    getStringPart(ADDRESS_DIVIDER, houseShort, userAddress.house) +
                    getStringPart(ADDRESS_DIVIDER, flatShort, userAddress.flat) +
                    getInvertedStringPart(ADDRESS_DIVIDER, userAddress.entrance, entranceShort) +
                    getInvertedStringPart(ADDRESS_DIVIDER, userAddress.floor, floorShort) +
                    getStringPart(ADDRESS_DIVIDER, "", userAddress.comment)
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
        return "${dateTime.date.datOfMonth} $monthName ${getTimeString(dateTime.time)}"
    }

    override fun getTimeString(time: Time): String {
        return "${addFirstZero(time.hourOfDay)}:${addFirstZero(time.minuteOfHour)}"
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

    override fun getOrderStatusName(orderStatus: OrderStatus): String {
        return when (orderStatus) {
            NOT_ACCEPTED -> resourcesProvider.getString(R.string.msg_status_not_accepted)
            ACCEPTED -> resourcesProvider.getString(R.string.msg_status_accepted)
            PREPARING -> resourcesProvider.getString(R.string.msg_status_preparing)
            SENT_OUT -> resourcesProvider.getString(R.string.msg_status_sent_out)
            DELIVERED -> resourcesProvider.getString(R.string.msg_status_delivered)
            DONE -> resourcesProvider.getString(R.string.msg_status_done)
            CANCELED -> resourcesProvider.getString(R.string.msg_status_canceled)
        }
    }

    override fun getPickupMethodString(isDelivery: Boolean): String {
        return if (isDelivery) {
            resourcesProvider.getString(R.string.msg_delivery)
        } else {
            resourcesProvider.getString(R.string.msg_pickup)
        }
    }
}