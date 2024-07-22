package com.bunbeauty.shared.presentation.base

import com.bunbeauty.shared.domain.asCommonStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

abstract class SharedStateViewModel<DS : BaseDataState, A : BaseAction, E : BaseEvent>(initDataState: DS) : SharedViewModel() {

    protected val mutableDataState = MutableStateFlow(initDataState)
    val dataState = mutableDataState.asCommonStateFlow()

    protected val mutableEvents = MutableStateFlow<List<E>>(emptyList())
    val events = mutableEvents.asCommonStateFlow()

    fun onAction(action: A) {
        reduce(action, mutableDataState.value)
    }

    protected abstract fun reduce(action: A, dataState: DS)

    fun consumeEvents(events: List<E>) {
        mutableEvents.update { list ->
            list - events.toSet()
        }
    }

    protected inline fun setState(block: DS.() -> DS) {
        mutableDataState.update(block)
    }

    protected inline fun addEvent(block: (DS) -> E) {
        mutableEvents.update { list ->
            list + block(mutableDataState.value)
        }
    }
}
