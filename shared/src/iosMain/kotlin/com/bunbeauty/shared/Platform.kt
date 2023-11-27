package com.bunbeauty.shared

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import platform.UIKit.UIDevice

actual val httpClientEngine: HttpClientEngine = Darwin.create()