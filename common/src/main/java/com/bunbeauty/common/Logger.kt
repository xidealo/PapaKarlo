package com.bunbeauty.common

//import android.util.Log

object Logger {

    private const val COMMON_TAG = "commonTag"
    private const val WORKER_TAG = "workerTag"

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
    const val CATEGORY_TAG = "categoryTag"
    const val AUTH_TAG = "authTag"
    const val DELIVERY_TAG = "deliveryTag"
    const val WEB_SOCKET_TAG = "webSocketTag"

    fun logD(tag: String, message: Any) {
        //if (isWorkerTag(tag)) {
        //    Log.d(WORKER_TAG, "$tag $message")
        //}
        //Log.d(COMMON_TAG, "$tag $message")
        //Log.d(tag, message.toString())
    }

    fun logE(tag: String, message: Any) {
        //Log.e(COMMON_TAG, "$tag $message")
        //Log.e(tag, message.toString())
    }

    private fun isWorkerTag(tag: String): Boolean {
        return tag.contains(Regex("Worker$"))
    }
}