package com.bunbeauty.shared.presentation.create_address

data class CreateAddressState(
    val streetItemList: List<StreetItem> = emptyList(),
    val state: State = State.Loading,
    val hasStreetError: Boolean = false,
    val hasHouseError: FieldError? = null,
    val hasFlatError: Boolean = false,
    val hasEntranceError: Boolean = false,
    val hasFloorError: Boolean = false,
    val hasCommentError: Boolean = false,
    val isCreateLoading: Boolean = false,
    val eventList: List<Event> = emptyList(),
) {

    val hasError =
        hasStreetError || hasHouseError != null
                || hasFlatError || hasEntranceError || hasFloorError || hasCommentError

    enum class FieldError {
        INCORRECT,
        LENGTH
    }

    sealed class State {
        object Success : State()
        object Loading : State()
        object Empty : State()
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

        override fun filter(query: String): Boolean {
            return query.lowercase().split(" ").all { queryPart ->
                name.lowercase().split(" ").any { namePart ->
                    namePart.startsWith(queryPart)
                }
            }
        }
    }

    operator fun plus(event: Event) = copy(eventList = eventList + event)
    operator fun minus(events: List<Event>) = copy(eventList = eventList - events.toSet())
}