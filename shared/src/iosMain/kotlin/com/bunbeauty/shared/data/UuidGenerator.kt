package com.bunbeauty.shared.data

import platform.Foundation.NSUUID

actual class UuidGenerator {

    actual fun generateUuid(): String {
        return NSUUID().UUIDString()
    }
}
