package com.bunbeauty.shared.presentation.base

import com.bunbeauty.shared.presentation.UIDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import kotlin.native.concurrent.ThreadLocal
import kotlin.native.runtime.GC
import kotlin.native.runtime.NativeRuntimeApi

@ThreadLocal
internal var createViewModelScope: () -> CoroutineScope = {
    CoroutineScope(SupervisorJob() + createUIDispatcher())
}

internal fun createUIDispatcher(): CoroutineDispatcher = UIDispatcher()

@Suppress("EmptyDefaultConstructor")
actual open class SharedViewModel actual constructor() {
    protected actual val sharedScope: CoroutineScope = createViewModelScope()

    @OptIn(NativeRuntimeApi::class)
    actual open fun onCleared() {
        sharedScope.cancel()

        dispatch_async(dispatch_get_main_queue()) { GC.collect() }
    }
}
