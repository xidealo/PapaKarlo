package com.bunbeauty.core.extension

import com.bunbeauty.core.Logger
import com.bunbeauty.core.Logger.COMMON_EXCEPTION_TAG
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

inline fun CoroutineScope.launchSafe(
    crossinline block: suspend CoroutineScope.() -> Unit,
    crossinline onError: suspend (Throwable) -> Unit,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    errorDispatcher: CoroutineDispatcher = Dispatchers.Main,
): Job {
    val exceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            Logger.logE(COMMON_EXCEPTION_TAG, "$throwable")
            launch(errorDispatcher) {
                onError(throwable)
            }
        }

    return launch(exceptionHandler + dispatcher) {
        block()
    }
}
