package com.bunbeauty.shared.data.network.logger

import com.google.firebase.crashlytics.FirebaseCrashlytics


class AndroidNetworkErrorLogger : NetworkErrorLogger {
    override fun logWarning(code: Int, message: String, throwable: Throwable) {
        val crashlytics = FirebaseCrashlytics.getInstance()
        crashlytics.setCustomKey(KEY_SEVERITY, SEVERITY_WARNING)
        crashlytics.setCustomKey(KEY_HTTP_CODE, code)
        crashlytics.log("Network warning [$code]: $message")
        crashlytics.recordException(throwable)
    }

    private companion object {
        const val KEY_SEVERITY = "severity"
        const val KEY_HTTP_CODE = "http_code"
        const val SEVERITY_WARNING = "warning"
    }
}
