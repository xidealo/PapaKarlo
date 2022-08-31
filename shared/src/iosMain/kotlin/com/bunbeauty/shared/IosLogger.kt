package com.bunbeauty.shared

actual fun log(logLevel: Logger.LogLevel, tag: String, message: String) {
    print("$tag : $message")
}