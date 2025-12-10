package com.bunbeauty.shared

object Constants {

    // COMPANY
    const val TEST_COMPANY_UUID = "fd483dcb-3f44-457f-b4d4-f82d2aa83b46"

    // NOTIFICATION

    const val NEWS_NOTIFICATION_PREFIX = "NEWS_"
    const val CHANNEL_ID = "1"

    // API PARAMETER

    const val COMPANY_UUID_PARAMETER = "companyUuid"
    const val CAFE_UUID_PARAMETER = "cafeUuid"
    const val USER_ADDRESS_UUID_PARAMETER = "userAddressUuid"
    const val CITY_UUID_PARAMETER = "cityUuid"
    const val UUID_PARAMETER = "uuid"
    const val QUERY_PARAMETER = "query"

    // HEADER

    const val AUTHORIZATION_HEADER = "Authorization"
    const val BEARER = "Bearer "

    // CART

    const val CART_PRODUCT_LIMIT = 99

    // TIME

    const val MIN_DEFERRED_HOURS_ADDITION = 1
    const val MIN_DEFERRED_MINUTES_ADDITION = 0
    const val MINUTES_IN_HOUR = 60
    const val SECONDS_IN_MINUTE = 60
    const val SECONDS_IN_HOUR = 60 * 60

    // ERROR

    const val NOT_FOUND_WITH_UUID = 400
    const val SOMETHING_WENT_WRONG = "Something went wrong"
    const val WRONG_CODE = "Wrong code"
    const val TOO_MANY_REQUESTS = "Too many requests"

    // DIVIDER

    const val TIME_DIVIDER = ":"
    const val CODE_DIVIDER = "-"
    const val WORKING_HOURS_DIVIDER = " - "
    const val COORDINATES_DIVIDER = ","
    const val VERSION_DIVIDER = "."
    const val ADDRESS_DIVIDER = ", "

    // SIGN

    const val RUBLE_CURRENCY = "₽"
    const val PERCENT = "%"

    // PHONE
    const val PHONE_CODE = "+7"

    // LINK

    const val MAPS_LINK = "https://maps.google.com/maps?daddr="
    const val PHONE_LINK = "tel:"

    // OTHER

    const val FAB_SNACKBAR_BOTTOM_PADDING = 68
}
