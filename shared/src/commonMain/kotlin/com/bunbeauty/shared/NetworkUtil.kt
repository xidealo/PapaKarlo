package com.bunbeauty.shared

import kotlinx.coroutines.flow.Flow

expect class NetworkUtil {
    fun observeIsOnline(): Flow<Boolean>
}
