package com.bunbeauty.shared

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual class OpenExternalSource {
    actual fun openPhone(uri: String) {
        openExternalSource(uri)
    }

    actual fun openMap(uri: String) {
        openExternalSource(uri)
    }

    actual fun openLink(uri: String) {
        openExternalSource(uri)
    }

    private fun openExternalSource(uri: String) {
        val url = NSURL.URLWithString(uri) ?: return
        UIApplication.sharedApplication.openURL(
            url = url,
            options = emptyMap<Any?, Any?>(),
            completionHandler = null
        )
    }
}