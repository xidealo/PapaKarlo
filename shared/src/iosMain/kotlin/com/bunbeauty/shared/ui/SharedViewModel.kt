package com.bunbeauty.shared.ui

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlin.native.internal.GC

@ThreadLocal
internal var createViewModelScope: () -> CoroutineScope = {
    CoroutineScope(createUIDispatcher())
}

internal fun createUIDispatcher(): CoroutineDispatcher = UIDispatcher()

@Suppress("EmptyDefaultConstructor")
actual open class SharedViewModel actual constructor() {
    protected actual val sharedScope: CoroutineScope = createViewModelScope()

    actual open fun onCleared() {
        sharedScope.cancel()

        dispatch_async(dispatch_get_main_queue()) { GC.collect() }
    }
}