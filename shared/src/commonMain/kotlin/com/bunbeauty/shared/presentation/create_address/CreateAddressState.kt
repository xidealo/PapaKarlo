package com.bunbeauty.shared.presentation.create_address

import com.bunbeauty.shared.domain.model.street.Street
import com.bunbeauty.shared.presentation.Suggestion

data class CreateAddressState(
    val streetList: List<Street> = emptyList(),
    val suggestedStreetList: List<Suggestion> = emptyList(),
    val state: State = State.Loading,

    val street: String = "",
    val hasStreetError: Boolean = false,

    val house: String = "",
    val hasHouseError: Boolean = false,

    val flat: String = "",
    val entrance: String = "",
    val floor: String = "",
    val comment: String = "",

    val isCreateLoading: Boolean = false,
    val eventList: List<Event> = emptyList(),
) {

    val hasError = hasStreetError || hasHouseError

    sealed class State {
        object Success : State()
        object Loading : State()
        data class Error(val throwable: Throwable) : State()
    }

    sealed interface Event {
        object AddressCreatedSuccess : Event
        object AddressCreatedFailed : Event
    }

    data class StreetItem(
        val uuid: String,
        val name: String,
    ) : AutoCompleteEntity {
        override val value: String = name
    }

    operator fun plus(event: Event) = copy(eventList = eventList + event)
    operator fun minus(events: List<Event>) = copy(eventList = eventList - events.toSet())
}