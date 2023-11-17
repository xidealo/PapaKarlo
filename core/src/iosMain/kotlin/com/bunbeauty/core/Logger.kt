package com.bunbeauty.core

actual fun log(logLevel: Logger.LogLevel, tag: String, message: String) {
    println("$tag : $message")
}