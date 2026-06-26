package com.bunbeauty.shared.data.network.logger

interface NetworkErrorLogger {
    fun logWarning(code: Int, message: String, throwable: Throwable)
}
