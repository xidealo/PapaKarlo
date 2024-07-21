package com.bunbeauty.shared.presentation.order_list

import com.bunbeauty.shared.domain.model.order.LightOrder

data class OrderListState(
    val orderList: List<LightOrder> = emptyList(),
    val eventList: List<Event> = emptyList(),
    val state: State = State.LOADING
) {

    enum class State {
        SUCCESS,
        EMPTY,
        LOADING
    }

    sealed interface Event
    class OpenOrderDetailsEvent(val orderUuid: String, val orderCode: String) : Event

    operator fun plus(event: Event) = copy(eventList = eventList + event)
    operator fun minus(events: List<Event>) = copy(eventList = eventList - events.toSet())
}
