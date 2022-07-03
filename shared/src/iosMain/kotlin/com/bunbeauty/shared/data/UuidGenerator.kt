package com.bunbeauty.shared.data

actual class UuidGenerator {

    actual fun generateUuid(): String = "NSUUID().UUIDString"
}