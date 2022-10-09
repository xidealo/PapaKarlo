package com.bunbeauty.shared

import io.ktor.client.engine.*
import io.ktor.client.engine.darwin.*
import platform.UIKit.UIDevice

actual class Platform actual constructor() {
    actual val platform: String =
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual val httpClientEngine: HttpClientEngine = Darwin.create()