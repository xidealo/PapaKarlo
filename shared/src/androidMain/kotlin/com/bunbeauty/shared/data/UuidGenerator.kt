package com.bunbeauty.shared.data

import java.util.UUID

actual class UuidGenerator {
    actual fun generateUuid(): String = UUID.randomUUID().toString()
}
