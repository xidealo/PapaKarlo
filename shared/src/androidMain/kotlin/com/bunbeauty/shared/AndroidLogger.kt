package com.bunbeauty.shared

import android.util.Log

actual fun log(logLevel: Logger.LogLevel, tag: String, message: String) {
    when (logLevel) {
        Logger.LogLevel.DEBUG -> {
            Log.d(tag, message)
        }
        Logger.LogLevel.ERROR -> {
            Log.e(tag, message)
        }
        Logger.LogLevel.WARNING -> {
            Log.w(tag, message)
        }
    }
}