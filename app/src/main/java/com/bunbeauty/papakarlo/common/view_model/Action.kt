package com.bunbeauty.papakarlo.common.view_model

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

interface Action

fun <T : Action> MutableSharedFlow<T>.send(action: T, coroutineScope: CoroutineScope) {
    coroutineScope.launch {
        emit(action)
    }
}
