package com.bunbeauty.shared

expect class OpenExternalSource {
    fun openPhone(uri: String)

    fun openMap(uri: String)

    fun openLink(uri: String)
}
