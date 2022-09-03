package com.bunbeauty.shared

import io.ktor.client.engine.*
import platform.UIKit.UIDevice
import io.ktor.client.engine.*
import io.ktor.client.engine.ios.*

actual class Platform actual constructor() {
    actual val platform: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

//actual val httpClientEngine: HttpClientEngine = Ios.create()