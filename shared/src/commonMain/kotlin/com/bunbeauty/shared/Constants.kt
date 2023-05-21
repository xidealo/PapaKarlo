package com.bunbeauty.shared

object Constants {

    // COMPANY
    const val TEST_COMPANY_UUID = "fd483dcb-3f44-457f-b4d4-f82d2aa83b46"

    const val PAPA_KARLO_TARGET_NAME = "papakarlo"
    const val PAPA_KARLO_FLAVOR_NAME = "papaKarlo"
    const val PAPA_KARLO_COMPANY_UUID = "7416dba5-2825-4fe3-abfb-1494a5e2bf99"
    const val PAPA_KARLO_VK_LINK = "https://vk.com/papakarlokimry"
    const val PAPA_KARLO_INSTAGRAM_LINK = "https://www.instagram.com/papakarlokimry"
    const val PAPA_KARLO_PLAY_MARKET_LINK = "https://play.google.com/store/apps/details?id=com.bunbeuaty.papakarlo"
    const val PAPA_KARLO_APP_STORE_LINK = "https://apps.apple.com/ru/app/%D0%BF%D0%B0%D0%BF%D0%B0-%D0%BA%D0%B0%D1%80%D0%BB%D0%BE/id6443966083"
    const val PAPA_KARLO_PAYMENT_INFO = "Оплатить заказ можно наличными, а также переводом по номеру карты или телефона"

    const val YULIAR_TARGET_NAME = "yuliar"
    const val YULIAR_FLAVOR_NAME = "yuliar"
    const val YULIAR_COMPANY_UUID = "8b91126f-be08-423a-b1dc-dea78ae79cd0"
    const val YULIAR_VK_LINK = "https://vk.com/kafe.yuliar"
    const val YULIAR_PLAY_MARKET_LINK = "https://play.google.com/store/apps/details?id=com.bunbeuaty.yuliar"
    const val YULIAR_APP_STORE_LINK = "https://apps.apple.com/ru/app/%D1%8E%D0%BB%D0%B8%D0%B0%D1%80/id6447322629"
    const val YULIAR_PAYMENT_INFO = "Оплатить заказ можно наличными или через терминал при получении"

    // NOTIFICATION

    const val NEWS_NOTIFICATION_PREFIX = "NEWS_"
    const val CHANNEL_ID = "1"

    // API PARAMETER

    const val COMPANY_UUID_PARAMETER = "companyUuid"
    const val CITY_UUID_PARAMETER = "cityUuid"

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
