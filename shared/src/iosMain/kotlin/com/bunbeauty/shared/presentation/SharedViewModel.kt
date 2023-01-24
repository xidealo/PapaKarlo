package com.bunbeauty.shared.presentation

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import kotlin.native.internal.GC

@ThreadLocal
internal var createViewModelScope: () -> CoroutineScope = {
    CoroutineScope(SupervisorJob() + createUIDispatcher())
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