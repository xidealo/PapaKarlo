package com.bunbeauty.shared.data

import java.util.*

actual class UuidGenerator {

    actual fun generateUuid(): String = UUID.randomUUID().toString()
}