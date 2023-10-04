package com.bunbeauty.shared.presentation.base

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class SharedStateViewModel<State: BaseState, Action: BaseAction, Event: BaseEvent>(
    initState: State
) : SharedViewModel() {

    protected val mutableState = MutableStateFlow(initState)
    val state = mutableState.asStateFlow()

    protected val mutableEvents = MutableStateFlow<List<Event>>(emptyList())
    val events = mutableEvents.asStateFlow()

    abstract fun handleAction(action: Action)

    fun consumeEvents(events: List<Event>) {
        mutableEvents.update { list ->
            list - events.toSet()
        }
    }

    protected inline fun state(block: (State) -> State) {
        mutableState.update(block)
    }

    protected inline fun event(block: (State) -> Event) {
        mutableEvents.update { list ->
            list + block(mutableState.value)
        }
    }


}