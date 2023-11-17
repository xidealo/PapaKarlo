package com.bunbeauty.core

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform