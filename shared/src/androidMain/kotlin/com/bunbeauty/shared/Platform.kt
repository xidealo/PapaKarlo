package com.bunbeauty.shared

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp


actual class Platform actual constructor() {
    actual val platform: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual val httpClientEngine: HttpClientEngine = OkHttp.create()