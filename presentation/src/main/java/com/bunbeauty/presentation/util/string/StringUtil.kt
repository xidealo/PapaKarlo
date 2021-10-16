package com.bunbeauty.presentation.util.string

import com.bunbeauty.common.Constants.ADDRESS_DIVIDER
import com.bunbeauty.common.Constants.TIME_DIVIDER
import com.bunbeauty.common.Constants.WORKING_HOURS_DIVIDER
import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.enums.OrderStatus.*
import com.bunbeauty.domain.model.Cafe
import com.bunbeauty.domain.model.Time
import com.bunbeauty.domain.model.address.CafeAddress
import com.bunbeauty.domain.model.address.UserAddress
import com.bunbeauty.domain.util.date_time.IDateTimeUtil
import com.bunbeauty.presentation.R
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import javax.inject.Inject

class StringUtil @Inject constructor(
    private val resourcesProvider: IResourcesProvider,
    private val dateTimeUtil: IDateTimeUtil
) : IStringUtil {

    override fun getCostString(cost: Int?): String {
        return if (cost == null) {
            ""
        } else {
            cost.toString() + resourcesProvider.getString(R.string.part_ruble)
        }
    }

    override fun getCafeAddressString(cafeAddress: CafeAddress?): String {
        return cafeAddress?.address ?: ""
    }

    override fun getCafeAddressString(cafe: Cafe?): String? {
        return cafe?.address
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

    override fun getWorkingHoursString(cafe: Cafe): String {
        return cafe.fromTime + WORKING_HOURS_DIVIDER + cafe.toTime
    }

    override fun getAddedToCartString(productName: String): String {
        return productName + resourcesProvider.getString(R.string.msg_cart_product_added)
    }

    override fun getRemovedFromCartString(productName: String): String {
        return productName + resourcesProvider.getString(R.string.msg_cart_product_removed)
    }

    override fun getDeliveryCostString(deliveryCost: Int): String {
        return if (deliveryCost == 0) {
            resourcesProvider.getString(R.string.msg_order_details_delivery_free)
        } else {
            getCostString(deliveryCost)
        }
    }

    override fun toStringTime(time: Long): String {
        return Time(time, 3).toStringTimeHHMM()
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

    override fun getTimeString(hour: Int, minute: Int): String {
        return hour.toString() + TIME_DIVIDER + addFirstZero(minute)
    }

    override fun getCodeString(code: String): String {
        return code
    }

    // Рассчитано на то, что кафе заканчивает работать до 24 ночи
    override fun getIsClosedMessage(cafe: Cafe): String {
        val beforeStart = dateTimeUtil.getMinutesFromNowToTime(cafe.fromTime)
        val beforeEnd = dateTimeUtil.getMinutesFromNowToTime(cafe.toTime)

        return when {
            (beforeStart >= 60) -> {
                val hoursBeforeStart = beforeStart / 60
                val minutesBeforeStart = beforeStart % 60
                "Закрыто. Откроется через " + hoursBeforeStart + "ч " + minutesBeforeStart + " мин"
            }
            (beforeStart in 1 until 60) -> {
                val minutesBeforeStart = beforeStart % 60
                "Закрыто. Откроется через $minutesBeforeStart мин"
            }
            (beforeEnd >= 60) -> {
                val hoursBeforeEnd = beforeEnd / 60
                val minutesBeforeEnd = beforeEnd % 60
                "Открыто. Закроется через $hoursBeforeEnd ч $minutesBeforeEnd мин"
            }
            (beforeEnd in 1 until 60) -> {
                val minutesBeforeEnd = beforeEnd % 60
                "Открыто. Закроется через $minutesBeforeEnd мин"
            }
            else -> {
                "Закрыто. Откроется завтра"
            }
        }
    }

    override fun getSizeString(weight: Int?): String {
        return if (weight == null) {
            ""
        } else {
            "$weight г"
        }
    }

    override fun getCountString(count: Int): String {
        return "x $count"
    }

    override fun getOrderStatusString(orderStatus: OrderStatus): String {
        return when (orderStatus) {
            NOT_ACCEPTED -> resourcesProvider.getString(R.string.msg_status_accepted)
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