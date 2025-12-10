package com.bunbeauty.shared

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

actual class NetworkUtil {
    actual fun observeIsOnline(): Flow<Boolean> {
        return flow { emit(true) }
    }
}