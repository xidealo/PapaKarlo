package com.bunbeauty.common

import android.util.Log

object Logger {

    const val TEST_TAG = "testTag"
    const val USER_TAG = "userTag"

    fun logD(tag: String, message: Any) {
        Log.d(tag, message.toString())
    }

    fun logE(tag: String, message: Any) {
        Log.e(tag, message.toString())
    }
}