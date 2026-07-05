package com.bunbeauty.shared.data.network.logger

class JsNetworkErrorLogger : NetworkErrorLogger {
    override fun logWarning(code: Int, message: String, throwable: Throwable) = Unit
}
