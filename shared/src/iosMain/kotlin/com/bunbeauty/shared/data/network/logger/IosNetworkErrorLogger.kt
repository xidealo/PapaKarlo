package com.bunbeauty.shared.data.network.logger

import cocoapods.FirebaseCrashlytics.FIRCrashlytics
import cocoapods.FirebaseCrashlytics.FIRExceptionModel
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
class IosNetworkErrorLogger : NetworkErrorLogger {
    override fun logWarning(
        code: Int,
        message: String,
        throwable: Throwable,
    ) {
        if (!NetworkErrorLogPolicy.shouldLog(code, throwable)) {
            return
        }

        val crashlytics = FIRCrashlytics.crashlytics()
        crashlytics.setCustomValue(SEVERITY_WARNING, forKey = KEY_SEVERITY)
        crashlytics.setCustomValue(code.toLong(), forKey = KEY_HTTP_CODE)
        crashlytics.logWithFormat("Network warning [$code]: $message")

        val exceptionModel =
            FIRExceptionModel.exceptionModelWithName(
                name = throwable::class.simpleName ?: "NetworkException",
                reason = throwable.message ?: message,
            )
        crashlytics.recordExceptionModel(exceptionModel)
    }

    private companion object {
        const val KEY_SEVERITY = "severity"
        const val KEY_HTTP_CODE = "http_code"
        const val SEVERITY_WARNING = "warning"
    }
}
