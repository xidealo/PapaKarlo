package com.bunbeauty.common

import android.util.Log

object Logger {

    const val TEST_TAG = "testTag"
    const val USER_TAG = "userTag"
    const val USER_ADDRESS_TAG = "userAddressTag"
    const val STREET_TAG = "streetTag"
    const val CITY_TAG = "cityTag"
    const val CAFE_TAG = "cafeTag"

    fun logD(tag: String, message: Any) {
        Log.d(tag, message.toString())
    }

    fun logE(tag: String, message: Any) {
        Log.e(tag, message.toString())
    }
}