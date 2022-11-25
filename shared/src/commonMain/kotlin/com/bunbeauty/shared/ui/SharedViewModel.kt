package com.bunbeauty.shared.ui

import kotlinx.coroutines.CoroutineScope

@Suppress("EmptyDefaultConstructor")
expect open class SharedViewModel() {
    protected val sharedScope: CoroutineScope

    open fun onCleared()
}