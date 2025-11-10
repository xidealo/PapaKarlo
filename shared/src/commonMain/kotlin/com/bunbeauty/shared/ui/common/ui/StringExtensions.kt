package com.bunbeauty.shared.ui.common.ui

import androidx.compose.runtime.Composable
import com.bunbeauty.shared.Constants.ADDRESS_DIVIDER
import com.bunbeauty.shared.domain.model.date_time.DateTime
import com.bunbeauty.shared.domain.model.date_time.Time
import com.bunbeauty.shared.domain.model.order.OrderAddress
import org.jetbrains.compose.resources.stringResource
import papakarlo.shared.generated.resources.Res
import papakarlo.shared.generated.resources.delivery_time
import papakarlo.shared.generated.resources.month_april
import papakarlo.shared.generated.resources.month_august
import papakarlo.shared.generated.resources.month_december
import papakarlo.shared.generated.resources.month_february
import papakarlo.shared.generated.resources.month_january
import papakarlo.shared.generated.resources.month_july
import papakarlo.shared.generated.resources.month_june
import papakarlo.shared.generated.resources.month_march
import papakarlo.shared.generated.resources.month_may
import papakarlo.shared.generated.resources.month_november
import papakarlo.shared.generated.resources.month_october
import papakarlo.shared.generated.resources.month_september
import papakarlo.shared.generated.resources.month_unknown
import papakarlo.shared.generated.resources.msg_address_entrance_short
import papakarlo.shared.generated.resources.msg_address_flat_short
import papakarlo.shared.generated.resources.msg_address_floor_short
import papakarlo.shared.generated.resources.msg_address_house_short
import papakarlo.shared.generated.resources.msg_delivery
import papakarlo.shared.generated.resources.msg_pickup
import papakarlo.shared.generated.resources.pickup_time

@Composable
fun DateTime.getDateTimeString(): String {
    val monthName =
        when (date.monthNumber) {
            1 -> Res.string.month_january
            2 -> Res.string.month_february
            3 -> Res.string.month_march
            4 -> Res.string.month_april
            5 -> Res.string.month_may
            6 -> Res.string.month_june
            7 -> Res.string.month_july
            8 -> Res.string.month_august
            9 -> Res.string.month_september
            10 -> Res.string.month_october
            11 -> Res.string.month_november
            12 -> Res.string.month_december
            else -> Res.string.month_unknown
        }.let { monthResourceId ->
            stringResource(monthResourceId)
        }
    return "${date.dayOfMonth} $monthName ${time.getTimeString()}"
}

@Composable
fun Time.getTimeString(): String = "${addFirstZero(hours)}:${addFirstZero(minutes)}"

private fun addFirstZero(number: Int): String =
    if (number < 10) {
        "0$number"
    } else {
        number.toString()
    }


fun getCountString(count: Int): String = "× $count"
fun getCountString(count: String): String = "× $count"

@Composable
fun OrderAddress.getOrderAddressString(): String =
    if (description.isNullOrEmpty()) {
        val houseShort = stringResource(Res.string.msg_address_house_short)
        val flatShort = stringResource(Res.string.msg_address_flat_short)
        val entranceShort = stringResource(Res.string.msg_address_entrance_short)
        val floorShort = stringResource(Res.string.msg_address_floor_short)
        street +
                getStringPart(ADDRESS_DIVIDER, houseShort, house) +
                getStringPart(ADDRESS_DIVIDER, flatShort, flat) +
                getInvertedStringPart(ADDRESS_DIVIDER, entrance, entranceShort) +
                getInvertedStringPart(ADDRESS_DIVIDER, floor, floorShort) +
                getStringPart(ADDRESS_DIVIDER, "", comment)
    } else {
        description
    }

fun getStringPart(
    divider: String,
    description: String,
    data: Any?,
): String =
    if (data == null || data.toString().isEmpty()) {
        ""
    } else {
        divider + description + data
    }

fun getInvertedStringPart(
    divider: String,
    data: Any?,
    description: String,
): String =
    if (data == null || data.toString().isEmpty()) {
        ""
    } else {
        divider + data + description
    }

@Composable
fun getPickupMethodString(isDelivery: Boolean): String =
    if (isDelivery) {
        stringResource(Res.string.msg_delivery)
    } else {
        stringResource(Res.string.msg_pickup)
    }

@Composable
fun getDeferredString(isDelivery: Boolean): String =
    if (isDelivery) {
        stringResource(Res.string.delivery_time)
    } else {
        stringResource(Res.string.pickup_time)
    }