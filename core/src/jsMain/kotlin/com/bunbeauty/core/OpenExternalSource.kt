package com.bunbeauty.core

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
        js("window.open(uri, '_blank')")
    }
}
