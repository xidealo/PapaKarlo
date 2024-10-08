package com.bunbeauty.core

import org.koin.core.qualifier.named

object Logger {

    private const val COMMON_TAG = "commonTag"

    const val TEST_TAG = "testTag"
    const val NAV_TAG = "navTag"

    const val VERSION_TAG = "versionTag"
    const val USER_TAG = "userTag"
    const val USER_ADDRESS_TAG = "userAddressTag"
    const val ORDER_TAG = "orderTag"
    const val STREET_TAG = "streetTag"
    const val CITY_TAG = "cityTag"
    const val CAFE_TAG = "cafeTag"
    const val MENU_PRODUCT_TAG = "menuProductTag"
    const val AUTH_TAG = "authTag"
    const val DELIVERY_TAG = "deliveryTag"
    const val WEB_SOCKET_TAG = "webSocketTag"
    const val NOTIFICATION_TAG = "notificationTag"
    const val NETWORK_TAG = "networkTag"
    const val COMMON_EXCEPTION_TAG = "commonExceptionTag"
    const val ANALYTIC_TAG = "analyticTag"

    fun logD(tag: String, message: Any) {
        log(LogLevel.DEBUG, tag = COMMON_TAG, message.toString())
        log(LogLevel.DEBUG, tag = tag, message.toString())
    }

    fun logE(tag: String, message: Any) {
        log(LogLevel.ERROR, tag = COMMON_TAG, message.toString())
        log(LogLevel.ERROR, tag = tag, message.toString())
    }

    fun logW(tag: String, message: Any) {
        log(LogLevel.WARNING, tag = COMMON_TAG, message.toString())
        log(LogLevel.WARNING, tag = tag, message.toString())
    }

    enum class LogLevel {
        DEBUG, ERROR, WARNING
    }
}

expect fun log(logLevel: Logger.LogLevel, tag: String, message: String)