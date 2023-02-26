package com.bunbeauty.shared.presentation.create_address

data class CreateAddressState(
    val streetItemList: List<StreetItem> = emptyList(),
    val state: State = State.Loading,
    val hasStreetError: Boolean = false,
    val houseFieldError: FieldError? = null,
    val hasFlatError: Boolean = false,
    val hasEntranceError: Boolean = false,
    val hasFloorError: Boolean = false,
    val hasCommentError: Boolean = false,
    val isCreateLoading: Boolean = false,
    val eventList: List<Event> = emptyList(),
    val suggestedStreetList: List<StreetItem> = emptyList(),
) {

    val hasError =
        hasStreetError || houseFieldError != null
                || hasFlatError || hasEntranceError || hasFloorError || hasCommentError

    enum class FieldError {
        INCORRECT,
        LENGTH
    }

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