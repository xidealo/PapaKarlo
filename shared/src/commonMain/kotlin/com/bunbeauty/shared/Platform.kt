package com.bunbeauty.shared

import io.ktor.client.engine.*

expect class Platform() {
    val platform: String
}

//expect val httpClientEngine: HttpClientEngine
