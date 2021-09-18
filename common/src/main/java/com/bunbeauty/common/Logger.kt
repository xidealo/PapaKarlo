package com.bunbeauty.common

import android.util.Log

object Logger {

    private const val COMMON_TAG = "commonTag"
    const val TEST_TAG = "testTag"
    const val USER_TAG = "userTag"
    const val USER_ADDRESS_TAG = "userAddressTag"
    const val STREET_TAG = "streetTag"
    const val CITY_TAG = "cityTag"
    const val CAFE_TAG = "cafeTag"
    const val MENU_PRODUCT_TAG = "menuProductTag"

    fun logD(tag: String, message: Any) {
        Log.d(COMMON_TAG, "$tag $message")
        Log.d(tag, message.toString())
    }

    fun logE(tag: String, message: Any) {
        Log.e(COMMON_TAG, "$tag $message")
        Log.e(tag, message.toString())
    }
}