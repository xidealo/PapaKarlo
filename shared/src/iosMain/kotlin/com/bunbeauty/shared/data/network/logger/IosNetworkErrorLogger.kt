package com.bunbeauty.shared.data.network.logger

class IosNetworkErrorLogger : NetworkErrorLogger {
    override fun logWarning(code: Int, message: String, throwable: Throwable) = Unit
}
