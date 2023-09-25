package com.bunbeauty.shared

object Constants {

    // COMPANY
    const val TEST_COMPANY_UUID = "fd483dcb-3f44-457f-b4d4-f82d2aa83b46"

    const val PAPA_KARLO_TARGET_NAME = "papakarlo"
    const val PAPA_KARLO_FLAVOR_NAME = "papaKarlo"
    const val PAPA_KARLO_COMPANY_UUID = "7416dba5-2825-4fe3-abfb-1494a5e2bf99"

    const val YULIAR_TARGET_NAME = "yuliar"
    const val YULIAR_FLAVOR_NAME = "yuliar"
    const val YULIAR_COMPANY_UUID = "8b91126f-be08-423a-b1dc-dea78ae79cd0"

    const val DJAN_TARGET_NAME = "djan"
    const val DJAN_FLAVOR_NAME = "djan"
    const val DJAN_COMPANY_UUID = "136ce426-15ab-49eb-ab78-19da43fca191"

    const val GUSTO_PUB_TARGET_NAME = "gustopub"
    const val GUSTO_PUB_FLAVOR_NAME = "gustopub"
    const val GUSTO_PUB_COMPANY_UUID = "e1d1474b-6fba-4dff-826f-48e89abc48e3"

    // NOTIFICATION

    const val NEWS_NOTIFICATION_PREFIX = "NEWS_"
    const val CHANNEL_ID = "1"

    // API PARAMETER

    const val COMPANY_UUID_PARAMETER = "companyUuid"
    const val CITY_UUID_PARAMETER = "cityUuid"
    const val UUID_PARAMETER = "uuid"

    // HEADER

    const val AUTHORIZATION_HEADER = "Authorization"
    const val BEARER = "Bearer "

    // CART

    const val CART_PRODUCT_LIMIT = 99

    // PAYMENT

    const val CARD_NUMBER_LABEL = "cart number"
    const val PHONE_NUMBER_LABEL = "phone number"

    // PATTERN

    const val DD_MMMM_HH_MM_PATTERN = "dd MMMM HH:mm"
    const val HH_MM_PATTERN = "HH:mm"

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
    const val ADDRESS_DIVIDER = ", "

    // PHONE
    const val PHONE_CODE = "+7"

    // LINK

    const val MAPS_LINK = "https://maps.google.com/maps?daddr="
    const val PHONE_LINK = "tel:"

}
