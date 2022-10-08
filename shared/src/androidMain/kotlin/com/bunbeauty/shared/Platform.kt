package com.bunbeauty.shared

import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*

actual class Platform actual constructor() {
    actual val platform: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual val httpClientEngine: HttpClientEngine = OkHttp.create()