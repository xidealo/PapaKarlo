package com.bunbeauty.shared

import io.ktor.client.engine.HttpClientEngine

expect class Platform() {
    val platform: String
}

expect val httpClientEngine: HttpClientEngine
