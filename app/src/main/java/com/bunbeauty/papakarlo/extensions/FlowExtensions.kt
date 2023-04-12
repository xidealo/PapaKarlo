package com.bunbeauty.papakarlo.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

inline fun <T> Flow<T>.startedLaunch(
    lifecycleOwner: LifecycleOwner,
    crossinline block: suspend (T) -> Unit
) {
    flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED).onEach {
        block(it)
    }.launchIn(lifecycleOwner.lifecycleScope)
}
