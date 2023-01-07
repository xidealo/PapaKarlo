package com.bunbeauty.shared.presentation.user_address_list

import com.bunbeauty.shared.domain.model.address.UserAddress

data class UserAddressListState(
    val userAddressList: List<UserAddress> = emptyList(),
    val eventList: List<Event> = emptyList(),
    val state: State = State.LOADING
) {

    enum class State {
        SUCCESS,
        EMPTY,
        LOADING,
    }

    sealed interface Event{
        object OpenCreateAddressEvent : Event
        object GoBack : Event
    }

    operator fun plus(event: Event) = copy(eventList = eventList + event)
    operator fun minus(events: List<Event>) = copy(eventList = eventList - events.toSet())
}