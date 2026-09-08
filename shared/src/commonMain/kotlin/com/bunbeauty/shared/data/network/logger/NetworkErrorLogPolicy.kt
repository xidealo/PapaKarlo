package com.bunbeauty.shared.data.network.logger

import kotlinx.coroutines.CancellationException

fun Throwable.isCoroutineCancellation(): Boolean = this is CancellationException

object NetworkErrorLogPolicy {
    fun shouldLog(
        httpCode: Int,
        throwable: Throwable,
    ): Boolean {
        if (throwable.isCoroutineCancellation()) {
            return false
        }
        if (isBenignNetworkFailure(throwable)) {
            return false
        }
        if (isExpectedHttpCode(httpCode)) {
            return false
        }
        return true
    }

    private fun isBenignNetworkFailure(throwable: Throwable): Boolean {
        val typeName = throwable::class.simpleName.orEmpty()
        if (typeName.contains("Timeout", ignoreCase = true)) {
            return true
        }
        if (typeName.contains("UnknownHost", ignoreCase = true)) {
            return true
        }
        if (typeName == "ConnectException" || typeName == "SocketException") {
            return true
        }
        return false
    }

    private fun isExpectedHttpCode(httpCode: Int): Boolean = httpCode in HTTP_CLIENT_ERROR_RANGE

    private val HTTP_CLIENT_ERROR_RANGE = 400..499
}
