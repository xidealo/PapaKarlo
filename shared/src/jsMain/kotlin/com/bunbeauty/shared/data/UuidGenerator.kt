package com.bunbeauty.shared.data

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

actual class UuidGenerator {
    @OptIn(ExperimentalUuidApi::class)
    actual fun generateUuid(): String = Uuid.random().toString()
}
