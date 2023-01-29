package com.bunbeauty.shared.presentation.profile

import com.bunbeauty.shared.domain.model.order.LightOrder

data class ProfileState(
    val lastOrder: LightOrder? = null,
    val state: State = State.LOADING,
    val eventList: List<Event> = emptyList(),
) {

    enum class State {
        AUTHORIZED,
        UNAUTHORIZED,
        ERROR,
        LOADING,
    }

    sealed interface Event {
        class OpenOrderDetails(val orderUuid: String, val orderCode: String) : Event
        object OpenSettings : Event
        object OpenAddressList : Event
        object OpenOrderList : Event
        object ShowPayment : Event
        object ShowFeedback : Event
        object ShowAboutApp : Event
        object OpenLogin : Event
    }

    operator fun plus(event: Event) = copy(eventList = eventList + event)
    operator fun minus(events: List<Event>) = copy(eventList = eventList - events.toSet())
}